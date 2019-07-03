package com.example.doutiao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(basePackages = "com.example.doutiao.mapper")
public class DoutiaoApplication extends SpringBootServletInitializer {
    //用于构建war文件并进行部署
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DoutiaoApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(DoutiaoApplication.class, args);
    }
}

