package com.achraf.eventhub.event;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record EventDto(
        Integer id,

        @NotBlank(message = "Title is required")
        @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @FutureOrPresent(message = "Date must be in the present or future")
        LocalDateTime date,

        @NotBlank(message = "Location is required")
        String location,

        @Min(value = 1, message = "Available seats must be at least 1")
        int availableSeats,

        @PositiveOrZero(message = "Price cannot be negative")
        double price,

        @NotNull(message = "Organizer ID is required")
        Integer organizerId
) {}
