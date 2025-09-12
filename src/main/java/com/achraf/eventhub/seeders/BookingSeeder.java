package com.achraf.eventhub.seeders;

import com.achraf.eventhub.booking.Booking;
import com.achraf.eventhub.booking.BookingService;
import com.achraf.eventhub.event.Event;
import com.achraf.eventhub.event.EventService;
import com.achraf.eventhub.user.User;
import com.achraf.eventhub.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(3) // runs after UserSeeder (1) and EventSeeder (2)
public class BookingSeeder implements CommandLineRunner {

    private final BookingService bookingService;
    private final EventService eventService;
    private final UserService userService;

    @Override
    public void run(String... args) {
        // use pageable.unpaged() to get all bookings without pagination
        if (bookingService.getAllBookings(Pageable.unpaged()).isEmpty()) {

            List<User> users = userService.getAllUsers();
            List<Event> events = eventService.getAllEvents(Pageable.unpaged()).getContent();

            if (!users.isEmpty() && !events.isEmpty()) {
                // safe indexing: if only one user/event exists, reuse index 0
                User u1 = users.get(0);
                User u2 = users.size() > 1 ? users.get(1) : users.get(0);

                Event e1 = events.get(0);
                Event e2 = events.size() > 1 ? events.get(1) : events.get(0);

                Booking b1 = Booking.builder()
                        .user(u1)
                        .event(e1)
                        .bookingDate(LocalDateTime.now())
                        .seatsBooked(2)
                        .build();

                Booking b2 = Booking.builder()
                        .user(u2)
                        .event(e2)
                        .bookingDate(LocalDateTime.now().minusDays(1))
                        .seatsBooked(4)
                        .build();

                // use service to persist so any business logic runs
                bookingService.createBooking(b1);
                bookingService.createBooking(b2);

                System.out.println("✅ Bookings seeded successfully!");
            }
        }
    }
}
