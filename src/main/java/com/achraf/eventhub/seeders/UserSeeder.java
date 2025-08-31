package com.achraf.eventhub.seeders;

import com.achraf.eventhub.user.User;
import com.achraf.eventhub.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) {
        if (userService.getAllUsers().isEmpty()) {
            User admin = new User();
            admin.setLastName("Admin User");
            admin.setEmail("admin@eventhub.com");
            admin.setPassword("password123");

            userService.createUser(admin);
            System.out.println("✅ Default admin user seeded.");
        }
    }
}
