package com.achraf.eventhub.event;

public record EventResponseDto(
        Integer id,
        String title,
        String description,
        String location,
        int availableSeats,
        double price
) {}
