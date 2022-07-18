package ru.itmo.kotiki;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import ru.itmo.kotiki.data.enums.AppUserRole;
import ru.itmo.kotiki.data.model.AppUser;
import ru.itmo.kotiki.data.model.Role;
import ru.itmo.kotiki.foreign.service.AppUserService;

import java.util.UUID;


@SpringBootApplication
@EntityScan("ru.itmo.kotiki.data.model")
public class KotikiApplication {
    public static void main(String[] args) {
        SpringApplication.run(KotikiApplication.class, args);
    }

    @Bean
    CommandLineRunner initDb(AppUserService appUserService) {
        return args -> {
            System.out.println("init started");
            Role adminRole = new Role(AppUserRole.ADMIN);
            Role userRole = new Role(AppUserRole.USER);

            System.out.println("roles created");
            appUserService.saveRole(adminRole);
            appUserService.saveRole(userRole);

            System.out.println("roles saved");
            AppUser user = new AppUser("Arkadiy", "Samolet", "12345");

            System.out.println("user created");
            appUserService.saveUser(user);

            System.out.println("user saved");
            appUserService.addRoleToUser(user.getUsername(), AppUserRole.ADMIN);
            System.out.println("role added to user");
        };
    }
}
