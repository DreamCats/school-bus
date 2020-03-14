/**
 * @program school-bus
 * @description: OrderServiceImpl
 * @author: mf
 * @create: 2020/03/04 21:50
 */

package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stylefeng.guns.rest.bus.IBusService;
import com.stylefeng.guns.rest.common.constants.RetCodeConstants;
import com.stylefeng.guns.rest.common.persistence.dao.OrderMapper;
import com.stylefeng.guns.rest.common.persistence.model.Order;
import com.stylefeng.guns.rest.modular.order.converter.OrderConvertver;
import com.stylefeng.guns.rest.myutils.UUIDUtils;
import com.stylefeng.guns.rest.order.IOrderSerice;
import com.stylefeng.guns.rest.order.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Component
@Service
public class OrderServiceImpl implements IOrderSerice {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderConvertver orderConvertver;

    @Reference
    private IBusService busService;

    @Override
    public NoTakeBusResponse getNoTakeOrdersById(NoTakeBusRequest request) {
        NoTakeBusResponse response = new NoTakeBusResponse();
        IPage<NoTakeDto> orderIPage = new  Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<NoTakeDto> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("user_id", request.getUserId())
                .and(o -> o.eq("order_status", "0")
                        .or()
                        .eq("order_status", "1"));
//        orderIPage = orderMapper.selectPage(orderIPage, queryWrapper);
        orderIPage = orderMapper.selectNoTakeOrders(orderIPage, queryWrapper);
        try {
            response.setCurrentPage(orderIPage.getCurrent());
            response.setPages(orderIPage.getPages());
            response.setPageSize(orderIPage.getSize());
            response.setTotal(orderIPage.getTotal());
            response.setNoTakeDtos(orderIPage.getRecords());
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getNoTakeOrdersById:",e);
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    /**
     * 该业务可以和上一个业务合并
     * @param request
     * @return
     */
    @Override
    public EvaluateResponse getEvaluateOrdersById(EvaluateRequest request) {
        EvaluateResponse response = new EvaluateResponse();
        IPage<EvaluateDto> orderIPage = new Page<>(request.getCurrentPage(), request.getPageSize());
        QueryWrapper<EvaluateDto> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("user_id", request.getUserId())
                .eq("order_status", "1")
                .eq("evaluate_status", request.getEvaluateStatus());
        try {
            orderIPage = orderMapper.selectEvaluateOrders(orderIPage, queryWrapper);
            response.setCurrentPage(orderIPage.getCurrent());
            response.setPages(orderIPage.getPages());
            response.setPageSize(orderIPage.getSize());
            response.setTotal(orderIPage.getTotal());
            response.setEvaluateDtos(orderIPage.getRecords());
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getEvaluateOrdersById:",e);
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public AddOrderResponse addOrder(AddOrderRequest request) {
        // 判断座位，如果重复，直接退出，否则更新场次的座位信息
        AddOrderResponse response = new AddOrderResponse();
        try {
            boolean selectedSeats = busService.selectedSeats(request.getSeatsIds(), request.getCountId());
            if (selectedSeats) {
                // b:true 说明重复
                response.setCode(RetCodeConstants.SELECTED_SEATS.getCode());
                response.setMsg(RetCodeConstants.SELECTED_SEATS.getMessage());
                return response;
            }
            // 更新场次的座位信息，并更新场次的座位是否已满
            boolean updateSeats = busService.updateSeats(request.getSeatsIds(), request.getCountId());
            if (!updateSeats) {
                response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
                response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
                return response;
            }
            // 添加订单了， 这里问题就来了，如果订单添加失败， 上面的更新场次的座位信息就要回滚
            // 如果上述更新座位信息放在添加订单之后呢？， 若更新座位异常，那么添加订单就要回滚，卧槽，事物就来了
            // 好像听说dubbo和springboot的事物发生冲突

            // 计算价格了
            String seatIds = request.getSeatsIds();
            Integer seatNumber = seatIds.split(",").length;
            Double countPrice = request.getCountPrice();
            Double totalPrice = getTotalPrice(seatNumber, countPrice);
            Order order = orderConvertver.res2Order(request);
            order.setOrderPrice(totalPrice);
            order.setEvaluateStatus("0"); // 未评价
            order.setOrderStatus("0"); // 未支付

            // 引入唯一id
            order.setUuid(UUIDUtils.getUUID());
            int insert = orderMapper.insert(order);// 插入 不判断了
            QueryWrapper<OrderDto> wrapper = new QueryWrapper<>();
            wrapper.eq("so.uuid", order.getUuid());
            OrderDto orderDto = orderMapper.selectOrderById(wrapper);
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
            response.setOrderDto(orderDto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addOrder");
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public OrderResponse selectOrderById(OrderRequest request) {
        OrderResponse response = new OrderResponse();
        try {
            QueryWrapper<OrderDto> wrapper = new QueryWrapper<>();
            wrapper.eq("so.uuid", request.getUuid());
            OrderDto orderDto = orderMapper.selectOrderById(wrapper);
            response.setOrderDto(orderDto);
            response.setCode(RetCodeConstants.SUCCESS.getCode());
            response.setMsg(RetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("selectOrderById", e);
            response.setCode(RetCodeConstants.DB_EXCEPTION.getCode());
            response.setMsg(RetCodeConstants.DB_EXCEPTION.getMessage());
            return response;
        }
        return response;
    }

    private double getTotalPrice(Integer seatNumbers, double countPrice) {
        BigDecimal seatNumbersDeci = new BigDecimal(seatNumbers);
        BigDecimal countPriceDeci = new BigDecimal(countPrice);
        BigDecimal result = seatNumbersDeci.multiply(countPriceDeci);
        // 四舍五入，取小数点后一位
        BigDecimal bigDecimal = result.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
