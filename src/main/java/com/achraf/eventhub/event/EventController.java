package com.achraf.eventhub.event;

import com.achraf.eventhub.user.User;
import com.achraf.eventhub.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<EventResponseDto>> getAllEvents(Pageable pageable) {
        Page<EventResponseDto> events = eventService.getAllEvents(pageable)
                .map(eventMapper::toResponseDto);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Integer id) {
        return eventService.getEventById(id)
                .map(eventMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@Valid @RequestBody EventDto dto) {
        User organizer = userRepository.findById(dto.organizerId())
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found"));

        Event savedEvent = eventService.createEvent(eventMapper.toEntity(dto, organizer));
        return ResponseEntity.ok(eventMapper.toResponseDto(savedEvent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(
            @PathVariable Integer id,
            @Valid @RequestBody EventDto dto
    ) {
        User organizer = userRepository.findById(dto.organizerId())
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found"));

        Event updatedEvent = eventMapper.toEntity(dto, organizer);
        Event savedEvent = eventService.updateEvent(id, updatedEvent);
        return ResponseEntity.ok(eventMapper.toResponseDto(savedEvent));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventResponseDto> patchEvent(
            @PathVariable Integer id,
            @RequestBody EventDto dto
    ) {
        User organizer = null;
        if (dto.organizerId() != null) {
            organizer = userRepository.findById(dto.organizerId())
                    .orElseThrow(() -> new IllegalArgumentException("Organizer not found"));
        }

        Event patchedEvent = eventMapper.toEntity(dto, organizer);
        Event savedEvent = eventService.patchEvent(id, patchedEvent);
        return ResponseEntity.ok(eventMapper.toResponseDto(savedEvent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // 🔎 Filtering endpoints
    @GetMapping("/search/location")
    public ResponseEntity<Page<EventResponseDto>> searchByLocation(
            @RequestParam String location,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                eventService.findByLocation(location, pageable).map(eventMapper::toResponseDto)
        );
    }

    @GetMapping("/search/upcoming")
    public ResponseEntity<Page<EventResponseDto>> getUpcomingEvents(
            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime from,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                eventService.findUpcomingEvents(from, pageable).map(eventMapper::toResponseDto)
        );
    }

    @GetMapping("/search/price")
    public ResponseEntity<Page<EventResponseDto>> getByPriceRange(
            @RequestParam double min,
            @RequestParam double max,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                eventService.findByPriceRange(min, max, pageable).map(eventMapper::toResponseDto)
        );
    }
}
