package com.achraf.eventhub.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Page<Event> getAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Integer id, Event updatedEvent) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        existing.setTitle(updatedEvent.getTitle());
        existing.setDescription(updatedEvent.getDescription());
        existing.setDate(updatedEvent.getDate());
        existing.setLocation(updatedEvent.getLocation());
        existing.setAvailableSeats(updatedEvent.getAvailableSeats());
        existing.setPrice(updatedEvent.getPrice());
        existing.setOrganizer(updatedEvent.getOrganizer());

        return eventRepository.save(existing);
    }

    public Event patchEvent(Integer id, Event partialEvent) {
        Event existing = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (partialEvent.getTitle() != null) existing.setTitle(partialEvent.getTitle());
        if (partialEvent.getDescription() != null) existing.setDescription(partialEvent.getDescription());
        if (partialEvent.getDate() != null) existing.setDate(partialEvent.getDate());
        if (partialEvent.getLocation() != null) existing.setLocation(partialEvent.getLocation());
        if (partialEvent.getAvailableSeats() != 0) existing.setAvailableSeats(partialEvent.getAvailableSeats());
        if (partialEvent.getPrice() != 0) existing.setPrice(partialEvent.getPrice());

        return eventRepository.save(existing);
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }

    public long countEvents() {
        return eventRepository.count();
    }


    // 🔎 Filtering
    public Page<Event> findByLocation(String location, Pageable pageable) {
        return eventRepository.findByLocationContainingIgnoreCase(location, pageable);
    }

    public Page<Event> findUpcomingEvents(LocalDateTime from, Pageable pageable) {
        return eventRepository.findByDateAfter(from, pageable);
    }

    public Page<Event> findByPriceRange(double min, double max, Pageable pageable) {
        return eventRepository.findByPriceBetween(min, max, pageable);
    }
}
