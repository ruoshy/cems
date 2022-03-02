package cn.rg.cems.enter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "cn.rg.cems")
//@EnableFeignClients
public class EnterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterApplication.class, args);
    }

}
