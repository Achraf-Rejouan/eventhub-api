package com.achraf.eventhub.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Page<Event> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    Page<Event> findByDateAfter(LocalDateTime date, Pageable pageable);
    Page<Event> findByPriceBetween(double min, double max, Pageable pageable);
}
