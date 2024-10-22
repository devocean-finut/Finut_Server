package com.finut.finut_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FinutServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinutServerApplication.class, args);
    }

}
