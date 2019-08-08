package com.qilu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qilu.mapper")
public class SmartcampusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartcampusApplication.class, args);
    }

}
