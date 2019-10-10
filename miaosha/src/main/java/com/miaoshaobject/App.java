package com.miaoshaobject;

import com.miaoshaobject.dao.UserInfoMapper;
import com.miaoshaobject.dataobject.UserInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"com.miaoshaobject"})
@RestController
@MapperScan("com.miaoshaobject.dao")
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }
}
