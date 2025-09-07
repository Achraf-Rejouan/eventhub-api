package com.achraf.eventhub.booking;

public record BookingResponseDto(
        Integer id,
        Integer eventId,
        Integer userId,
        Integer seatsBooked
) {}
