package ru.itmo.kotiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("ru.itmo.kotiki.data.model")
public class OwnerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwnerServiceApplication.class, args);
    }
}
