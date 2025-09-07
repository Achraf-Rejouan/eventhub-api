package com.achraf.eventhub.seeders;

import com.achraf.eventhub.event.Event;
import com.achraf.eventhub.event.EventService;
import com.achraf.eventhub.user.Role;
import com.achraf.eventhub.user.User;
import com.achraf.eventhub.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Order(2) // Ensure this runs after UserSeeder
public class EventSeeder implements CommandLineRunner {

    private final EventService eventService;
    private final UserService userService;

    @Override
    public void run(String... args) {
        if (eventService.getAllEvents().isEmpty()) {
            // Fetch a persisted user (admin or regular user)
            User admin = userService.getAllUsers().stream()
                    .filter(user -> user.getRole() == Role.ADMIN)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No admin user found. Please seed users first."));

            Event event1 = new Event();
            event1.setTitle("Tech Conference 2025");
            event1.setDescription("A conference about the latest in technology.");
            event1.setLocation("Tunis Convention Center");
            event1.setDate(LocalDateTime.of(2025, 9, 15, 9, 0));
            event1.setAvailableSeats(300);
            event1.setPrice(199.99);
            event1.setOrganizer(admin);

            Event event2 = new Event();
            event2.setTitle("Music Festival");
            event2.setDescription("A weekend of live music and fun.");
            event2.setLocation("Carthage Amphitheatre");
            event2.setDate(LocalDateTime.of(2025, 10, 20, 15, 0));
            event2.setAvailableSeats(500);
            event2.setPrice(99.99);
            event2.setOrganizer(admin);

            eventService.createEvent(event1);
            eventService.createEvent(event2);

            System.out.println("✅ Seeded events into the database.");
        }
    }
}

