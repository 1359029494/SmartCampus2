package com.qilu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@MapperScan("com.qilu.mapper")
@EnableTransactionManagement
public class SmartcampusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartcampusApplication.class, args);
    }



}
