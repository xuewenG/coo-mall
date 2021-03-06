package cn.edu.hfut.coomall.web.merchant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"cn.edu.hfut.coomall"})
@ComponentScan(basePackages = {"cn.edu.hfut.coomall"})
public class CoomallWebMerchantApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoomallWebMerchantApplication.class, args);
    }

}
