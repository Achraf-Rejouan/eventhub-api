package com.achraf.eventhub.booking;

import com.achraf.eventhub.event.Event;
import com.achraf.eventhub.user.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingMapper {

    public Booking toEntity(BookingDto dto, User user, Event event) {
        if (dto == null) return null;
        return Booking.builder()
                .id(dto.id())
                .event(event)
                .user(user)
                .bookingDate(LocalDateTime.now())
                .seatsBooked(dto.seatsBooked())
                .build();
    }

    public BookingResponseDto toResponseDto(Booking booking) {
        if (booking == null) return null;
        return new BookingResponseDto(
                booking.getId(),
                booking.getEvent().getId(),
                booking.getUser().getId(),
                booking.getSeatsBooked()
        );
    }
}
