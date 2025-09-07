package com.achraf.eventhub.event;

import com.achraf.eventhub.user.User;
import com.achraf.eventhub.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        List<EventResponseDto> events = eventService.getAllEvents()
                .stream()
                .map(eventMapper::toResponseDto)
                .toList();
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
