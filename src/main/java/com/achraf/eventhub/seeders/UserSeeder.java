package com.achraf.eventhub.seeders;

import com.achraf.eventhub.user.Role;
import com.achraf.eventhub.user.User;
import com.achraf.eventhub.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1) // Ensure this runs before EventSeeder
public class UserSeeder implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) {
        if (userService.getAllUsers().isEmpty()) {
            User admin = new User();
            admin.setUserName("Admin User");
            admin.setEmail("admin@eventhub.com");
            admin.setPassword("password123");
            admin.setRole(Role.ADMIN);

            User user = new User();
            user.setUserName("Regular User");
            user.setEmail("user@eventhub.com");
            user.setPassword("password321");
            user.setRole(Role.USER);

            userService.createUser(admin);
            userService.createUser(user);
            System.out.println("✅ Default admin user seeded.");
            System.out.println("✅ Default regular user seeded.");
        }
    }
}
