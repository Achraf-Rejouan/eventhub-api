package com.achraf.eventhub.event;

import com.achraf.eventhub.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDateTime date;

    @NotBlank(message = "Location is required")
    private String location;

    @Min(value = 1, message = "Available seats must be at least 1")
    private int availableSeats;

    @PositiveOrZero(message = "Price cannot be negative")
    private double price;

    // Each event is organized by one user
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonBackReference
    @NotNull(message = "Organizer is required")
    private User organizer;
}
