
from itertools import groupby
from numpy import *
import os
from PIL import Image
from MyKnn import My_knn


class captcha(object):
    # 初始化文件夹
    def __init__(self,yzm_folder,split_folder,traindatas_folder,testdatas_folder,train_size):
        '''

        '''
        self.yzm_folder = yzm_folder
        self.split_folder = split_folder
        self.traindatas_folder = traindatas_folder
        self.testdatas_folder = testdatas_folder
        self.train_size = train_size
    # 灰度 二值化
    def binarizing(self,img, threshold):
        """传入image对象进行灰度、二值处理"""
        img = img.convert("L")  # 转灰度
        pixdata = img.load()
        w, h = img.size
        # 遍历所有像素，大于阈值的为黑色
        for y in range(h):
            for x in range(w):
                if pixdata[x, y] < threshold:
                    pixdata[x, y] = 0
                else:
                    pixdata[x, y] = 255
        return img
    # 垂直投影
    def vertical(self,img):
        """传入二值化后的图片进行垂直投影"""
        pixdata = img.load()
        w, h = img.size
        result = []
        for x in range(w):
            black = 0
            for y in range(h):
                if pixdata[x, y] == 0:
                    black += 1
            result.append(black)
        return result
    # 得到left_x
    def get_split_x(self,im,name):
        xs,ys = im.size
        pixdata = im.load()
        left_x = []
        for x in range(1,xs):
            black = 0
            for y in range(1,ys):
                if pixdata[x,y] == 0:
                    for i in  range(1,ys):
                        if x == 1:
                            left_x.append(x - 1)
                            break
                        else:
                            if pixdata[x - 1,i] == 0:
                                black = black + 1
                    if black == 0:
                        left_x.append(x - 1)
                        break
        im.crop((left_x[0],0,xs,ys)).save(name)
        img = Image.open(name)
        return left_x[0],img
    # 得到right_x
    def get_split_y(self,im,name):
        xs, ys = im.size
        pixdata = im.load()
        right_x = []
        try:
            for x in range(1, xs):
                black = 0
                for y in range(1, ys):
                    if pixdata[xs - x -1 , y] == 0:
                        for i in range(1, ys):
                            if pixdata[xs - x + 1, i] == 0:
                                black = black + 1
                        if black == 0:
                            right_x.append(xs - x + 2)
                            break
            im.crop((0, 0, right_x[0], ys)).save(name)
            img = Image.open(name)
            return right_x[0], img
        except IndexError:
            return None
    # 获取四个的起始X
    def get_start_xx(self,hist_width):
        """根据图片垂直投影的结果来确定起点
           hist_width中间值 前后取4个值 再这范围内取最小值
        """
        mid = len(hist_width) // 2  # 注意py3 除法和py2不同
        # print('四中间值',mid)
        temp = hist_width[mid - 3:mid + 4]
        if min(temp) < 4:
            return mid - 3 + temp.index(min(temp))
        else:
            return None
    # 获取粘连字符的起始x
    def get_start_x(self,hist_width):
        """根据图片垂直投影的结果来确定起点
           hist_width中间值 前后取4个值 再这范围内取最小值
        """
        mid = len(hist_width) // 2  # 注意py3 除法和py2不同
        temp = hist_width[mid - 10:mid + 11]
        # print('二中间值',mid)
        if min(temp) < 5:
            return mid - 10 + temp.index(min(temp))
        else:
            return None
    # 获取邻居5个点像素数据
    def get_nearby_pix_value(self,img_pix,x,y,j):
        """获取临近5个点像素数据"""
        try:
            if j == 1:
                return 0 if img_pix[x - 1, y + 1] == 0 else 1
            elif j == 2:
                return 0 if img_pix[x, y + 1] == 0 else 1
            elif j == 3:
                return 0 if img_pix[x + 1, y + 1] == 0 else 1
            elif j == 4:
                return 0 if img_pix[x + 1, y] == 0 else 1
            elif j == 5:
                return 0 if img_pix[x - 1, y] == 0 else 1
            else:
                raise Exception("get_nearby_pix_value error")
        except IndexError:
            # print('留点多余两行空白，或者吃了什么东西了，能走到边缘路径，也是狗狗的了。')
            return 7
    # 获取滴水路径
    def get_end_route(self,img,start_x,height):
        """获取滴水路径"""
        left_limit = 0
        right_limit = img.size[0] - 1
        end_route = []
        cur_p = (start_x, 0)
        last_p = cur_p
        end_route.append(cur_p)
        while cur_p[1] < (height - 1):
            sum_n = 0
            max_w = 0
            next_x = cur_p[0]
            next_y = cur_p[1]
            pix_img = img.load()
            for i in range(1, 6):
                cur_w = self.get_nearby_pix_value(pix_img, cur_p[0], cur_p[1], i) * (6 - i)
                sum_n += cur_w
                if max_w < cur_w:
                    max_w = cur_w
            if sum_n == 0:
                # 如果全黑则看惯性
                max_w = 4
            if sum_n == 15:
                max_w = 6
            if max_w == 1:
                next_x = cur_p[0] - 1
                next_y = cur_p[1]
            elif max_w == 2:
                next_x = cur_p[0] + 1
                next_y = cur_p[1]
            elif max_w == 3:
                next_x = cur_p[0] + 1
                next_y = cur_p[1] + 1
            elif max_w == 5:
                next_x = cur_p[0] - 1
                next_y = cur_p[1] + 1
            elif max_w == 6:
                next_x = cur_p[0]
                next_y = cur_p[1] + 1
            elif max_w == 4:
                if next_x > cur_p[0]:
                    # 向右
                    next_x = cur_p[0] + 1
                    next_y = cur_p[1] + 1
                if next_x < cur_p[0]:
                    next_x = cur_p[0]
                    next_y = cur_p[1] + 1
                if sum_n == 0:
                    next_x = cur_p[0]
                    next_y = cur_p[1] + 1
            else:
                return None
            if last_p[0] == next_x and last_p[1] == next_y:
                if next_x < cur_p[0]:
                    max_w = 5
                    next_x = cur_p[0] + 1
                    next_y = cur_p[1] + 1
                else:
                    max_w = 3
                    next_x = cur_p[0] - 1
                    next_y = cur_p[1] + 1
            last_p = cur_p
            if next_x > right_limit:
                next_x = right_limit
                next_y = cur_p[1] + 1
            if next_x < left_limit:
                next_x = left_limit
                next_y = cur_p[1] + 1
            cur_p = (next_x, next_y)
            end_route.append(cur_p)
        # print(end_route)
        return end_route
    # 得到开始和结束的路径之后开始切割
    def do_split(self,source_image, starts, filter_ends):
        """
           具体实行切割
           : param starts: 每一行的起始点 tuple of list
           : param ends: 每一行的终止点
        """
        left = starts[0][0]
        top = starts[0][1]
        right = filter_ends[0][0]
        bottom = filter_ends[0][1]
        pixdata = source_image.load()
        for i in range(len(starts)):
            left = min(starts[i][0], left)
            top = min(starts[i][1], top)
            right = max(filter_ends[i][0], right)
            bottom = max(filter_ends[i][1], bottom)
        # print(left,top,right,bottom)
        width = right - left + 1
        height = bottom - top + 1
        image = Image.new('L', (width, height), 255)
        for i in range(height):
            start = starts[i]
            end = filter_ends[i]
            for x in range(start[0], end[0] + 1):
                if pixdata[x, start[1]] == 0:
                    image.putpixel((x - left, start[1] - top), 0)
        return image
    # 滴水算法过程
    def drop_water_process(self,im,namecount,split=False):
        img_list = []
        width,height  = im.size
        # 垂直投影
        if split == False:
            hist_width = self.vertical(im)
            # 获取起始坐标
            start_x = self.get_start_x(hist_width)
        else:
            hist_width = self.vertical(im)
            # print('投影',hist_width,len(hist_width))
            start_x = self.get_start_xx(hist_width)
        if start_x:
            # 开始滴水算法
            start_route = []
            for y in range(height):
                start_route.append((0, y))
            end_route = self.get_end_route(im, start_x, height)
            if end_route:
                filter_end_route = [max(list(k)) for _, k in groupby(end_route, lambda x: x[1])]  # 注意这里groupby ,去掉重复的
                img1 = self.do_split(im, start_route, filter_end_route)
                if split == True:
                    self.img_resize(img1,self.split_folder + str(namecount)+ '_' + str(namecount) + '.png',self.train_size)
                    img_list.append(img1)
                else:
                    left_x, img = self.get_split_x(img1, self.yzm_folder + str(namecount) + '.png')
                    img_list.append(img)
                start_route = list(map(lambda x: (x[0] + 1, x[1]), filter_end_route))  # python3中map不返回list需要自己转换
                end_route = []
                for y in range(height):
                    end_route.append((width - 1, y))
                img2 = self.do_split(im, start_route, end_route)
                if split == True:
                    # img2.save(self.split_folder + str(namecount)+ '_' + str(namecount + 1) + '.png')
                    self.img_resize(img2, self.split_folder + str(namecount) + '_' + str(namecount + 1) + '.png', self.train_size)
                    img_list.append(img2)
                else:
                    try:
                        right_x, img1 = self.get_split_y(img2, self.yzm_folder + str(namecount + 1) + '.png')
                        left_x, img1  = self.get_split_x(img1,self.yzm_folder +str(namecount + 1) + '.png')
                        img_list.append(img1)
                    except TypeError:
                        return None
                return img_list
            else:
                return None
        else:
            return None
    # 重新定义尺寸
    def img_resize(self,im,name,resize):
        im.resize((resize)).save(name)
    # 切四份
    def split_four(self,img_list):
        if img_list:
            img_one = self.drop_water_process(img_list[0],0,split=True)
            img_twe = self.drop_water_process(img_list[1],1,split=True)
            if img_one and img_twe:
                return img_one,img_twe
            else:
                return None
        else:
            return None
    # 单单执行切割总过程
    def split_process(self,namecount):
        im = Image.open(self.yzm_folder + str(namecount) + '.png')
        # 灰度，二值化
        im = self.binarizing(im, 140)
        # 这里要一分为二
        img_list = self.drop_water_process(im,namecount,split=False)
        # 分 四
        if img_list:
            img_four_result = self.split_four(img_list)
            if img_four_result:
                return img_four_result
            else:
                return None
        else:
            return None

    # 获取文件夹列表
    def get_folder_list(self, folder):
        file_list = os.listdir(folder)
        return file_list
    # 自动输入验证码，填充，全自动训练样本
    def get_auto_input(self,yzm):
        yzm_list = list(yzm)
        return yzm_list
    # 为了训练，提前手动输入验证码，得到训练样本
    def get_user_input(self):
        cmd = input(
            '''
            识别出图片对应的字母或者数字：
            如果用户读不出来，请按yy跳过，继续下一个训练
            '''
        )
        if cmd == 'yy':
            return None
        else:
            return cmd
     # 为了训练，制作训练样本的名字
    # 自动输入验证码，全自动构造样本名字
    def get_auto_train_name(self,yzm,train_folder,yzm_i):
        file_list = self.get_folder_list(train_folder)
        file_list_lenth = len(file_list)
        file_list_names = []
        attrs = {}
        for i in range(0, file_list_lenth):
            file_list_name = file_list[i]
            file_list_name = file_list_name.split('.')[0]
            name = file_list_name.split('_')[0]
            file_list_names.append(name)
        for i in range(0, len(file_list_names)):
            attrs[file_list_names[i]] = attrs.get(file_list_names[i], 0) + 1
        yzm_list = self.get_auto_input(yzm)
        attrs[yzm_list[yzm_i]] = attrs.get(yzm_list[yzm_i], 0) + 1
        file_name = yzm_list[yzm_i] + '_' + str(attrs[yzm_list[yzm_i]]) + '.txt'
        return file_name
    # 制作训练数据的名字
    def get_traindata_name(self, split_folder):
        file_list = self.get_folder_list(split_folder)
        file_list_lenth = len(file_list)
        file_list_names = []
        attrs = {}
        for i in range(0, file_list_lenth):
            file_list_name = file_list[i]
            file_list_name = file_list_name.split('.')[0]
            name = file_list_name.split('_')[0]
            file_list_names.append(name)
        for i in range(0, len(file_list_names)):
            attrs[file_list_names[i]] = attrs.get(file_list_names[i], 0) + 1
        cmd = self.get_user_input()
        if cmd != None:
            attrs[cmd] = attrs.get(cmd, 0) + 1
            file_name = cmd + '_' + str(attrs[cmd]) + '.txt'
            return file_name
        else:
            return None
    # 为了训练，制作样本，归一化
    def get_traindata_guiyihua(self, split_folder, traindata_folder, yzm=None, auto=False):
        file_list = os.listdir(split_folder)
        file_list_len = len(file_list)
        for i in range(0, file_list_len):
            im = Image.open(split_folder + file_list[i])
            # print(file_list[i] + ':')
            if auto == False:
                new_file_name = self.get_traindata_name(traindata_folder)
            else:
                new_file_name = self.get_auto_train_name(yzm, traindata_folder, i)
                # print(new_file_name)
            if new_file_name == None:
                continue
            else:
                fh = open(traindata_folder + new_file_name, 'w')
                width = im.size[0]
                heigh = im.size[1]
                for y in range(0, heigh):
                    for x in range(0, width):
                        point = im.getpixel((x, y))
                        if point == 0:
                            # 黑色
                            fh.write('1')
                        else:
                            # 白色
                            fh.write('0')
                    fh.write('\n')
                fh.close()
    # 制作训练数据
    def make_traindatas_process(self,yzm=None,auto=False):
        self.get_traindata_guiyihua(self.split_folder,self.traindatas_folder,yzm,auto)
    # 制作文件数组
    def get_file_arry(self,file,size):
        arr = []
        fh = open(file)
        for y in range(0, size[1]):
            thisline = fh.readline()
            for x in range(0, size[0]):
                arr.append(int(thisline[x]))
        return arr
    # 获取txt文件标签属性
    def get_train_cate(self, thisname):
        file_str = thisname.split('.')[0]
        cate = (file_str.split('_')[0])
        return cate
        # 制作所有训练样本的数字
    def get_train_datas(self, traindata_folder, size):
        train_cates = []
        train_files = os.listdir(traindata_folder)
        train_num = len(train_files)
        # 用一个数组存储所有训练数据，行：文件总数，列：1024
        train_arry = zeros((train_num, int(size[0]) * int(size[1])))
        for i in range(0, train_num):
            thisname = train_files[i]
            thiscate = self.get_train_cate(thisname)
            train_cates.append(thiscate)
            train_arry[i, :] = self.get_file_arry(traindata_folder + thisname, size)
        return train_arry, train_cates
    # 制作测试数据
    def get_test_datas(self, split_folder, testdata_folder):
        file_list = self.get_folder_list(split_folder)
        file_list_lenth = len(file_list)
        for i in range(0, file_list_lenth):
            thisname_str = file_list[i].split('.')[0]
            thisname = thisname_str + '.txt'
            fh = open(testdata_folder + thisname, 'w')
            im = Image.open(split_folder + file_list[i])
            width = im.size[0]
            heigh = im.size[1]
            for y in range(0, heigh):
                for x in range(0, width):
                    point = im.getpixel((x, y))
                    if point == 0:
                        # 黑色
                        fh.write('1')
                    else:
                        # 白色
                        fh.write('0')
                fh.write('\n')
            fh.close()
    # 调试测试数据
    def make_testdatas_process(self):
        yzm = ''
        self.get_test_datas(self.split_folder, self.testdatas_folder)
        train_arry, cates = self.get_train_datas(self.traindatas_folder, self.train_size)
        test_list = os.listdir(self.testdatas_folder)
        for i in range(0, len(test_list)):
            testfile = self.testdatas_folder + test_list[i]
            test_arry = self.get_file_arry(testfile, self.train_size)
            myknn = My_knn(3, test_arry, train_arry, cates)
            knn_value = myknn.knn()
            yzm = yzm + str(knn_value)
        return yzm

