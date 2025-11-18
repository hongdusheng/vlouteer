package com.itcao.volunteer_back_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itcao.volunteer_back_system.mapper")
public class VolunteerBackSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolunteerBackSystemApplication.class, args);
        System.out.println("启动成功");
    }

}
