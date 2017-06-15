package com.cly.crystal.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = "classpath:crystal_spring.xml")
public class CrystalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrystalApplication.class, args);
    }

}
