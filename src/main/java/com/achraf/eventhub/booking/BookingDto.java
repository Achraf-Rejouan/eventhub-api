package com.achraf.eventhub.booking;

import com.achraf.eventhub.event.EventDto;
import com.achraf.eventhub.user.UserDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class BookingDto {

    public static class CreateRequest {
        @NotNull(message = "Event ID is required")
        private Long eventId;

        @NotNull(message = "Number of seats is required")
        @Min(value = 1, message = "Number of seats must be at least 1")
        private Integer seatsBooked;

        // Constructors
        public CreateRequest() {}

        // Getters and Setters
        public Long getEventId() { return eventId; }
        public void setEventId(Long eventId) { this.eventId = eventId; }

        public Integer getSeatsBooked() { return seatsBooked; }
        public void setSeatsBooked(Integer seatsBooked) { this.seatsBooked = seatsBooked; }
    }

    public static class Response {
        private Long id;
        private EventDto.Response event;
        private UserDto.UserResponse user;
        private LocalDateTime bookingDate;
        private Integer seatsBooked;

        // Constructors
        public Response() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public EventDto.Response getEvent() { return event; }
        public void setEvent(EventDto.Response event) { this.event = event; }

        public UserDto.UserResponse getUser() { return user; }
        public void setUser(UserDto.UserResponse user) { this.user = user; }

        public LocalDateTime getBookingDate() { return bookingDate; }
        public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

        public Integer getSeatsBooked() { return seatsBooked; }
        public void setSeatsBooked(Integer seatsBooked) { this.seatsBooked = seatsBooked; }
    }
}