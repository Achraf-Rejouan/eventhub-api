package com.achraf.eventhub.seeders;

import com.achraf.eventhub.booking.Booking;
import com.achraf.eventhub.booking.BookingRepository;
import com.achraf.eventhub.event.Event;
import com.achraf.eventhub.event.EventRepository;
import com.achraf.eventhub.user.User;
import com.achraf.eventhub.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingSeeder implements CommandLineRunner {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (bookingRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            List<Event> events = eventRepository.findAll();

            if (!users.isEmpty() && !events.isEmpty()) {
                Booking b1 = Booking.builder()
                        .user(users.get(0))
                        .event(events.get(0))
                        .bookingDate(LocalDateTime.now())
                        .seatsBooked(2)
                        .build();

                Booking b2 = Booking.builder()
                        .user(users.get(1 % users.size()))
                        .event(events.get(1 % events.size()))
                        .bookingDate(LocalDateTime.now().minusDays(1))
                        .seatsBooked(4)
                        .build();

                bookingRepository.saveAll(List.of(b1, b2));
                System.out.println("✅ Bookings seeded successfully!");
            }
        }
    }
}
