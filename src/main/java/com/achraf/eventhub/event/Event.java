package com.achraf.eventhub.event;

import com.achraf.eventhub.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    private String title;

    private String description;

    private LocalDateTime date;

    private String location;

    private int availableSeats;

    private double price;

    // Each event is organized by one user
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonBackReference
    private User organizer;
}
