package com.spring.afterend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.spring.afterend")
//mybatisplus需要扫描包，所以引用这个注解来扫面该路径下面的包
public class AfterEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(AfterEndApplication.class, args);
    }

}
