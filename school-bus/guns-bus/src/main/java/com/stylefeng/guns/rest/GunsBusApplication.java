package com.stylefeng.guns.rest;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class GunsBusApplication {

    public static void main(String[] args) {
        SpringApplication.run(GunsBusApplication.class, args);
    }
}
