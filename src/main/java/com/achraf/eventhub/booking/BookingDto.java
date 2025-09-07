package com.achraf.eventhub.booking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookingDto(
        Integer id,

        @NotNull(message = "Event ID is required")
        Integer eventId,

        @NotNull(message = "User ID is required")
        Integer userId,

        @Min(value = 1, message = "At least one seat must be booked")
        Integer seatsBooked
) {}
