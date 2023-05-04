package com.catchthemoment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@EnableTransactionManagement
@Profile({"dev,prod"})
public class CatchTheMomentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatchTheMomentApplication.class);
    }
}
