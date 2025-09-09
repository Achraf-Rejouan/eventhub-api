package com.achraf.eventhub.booking;

import com.achraf.eventhub.event.Event;
import com.achraf.eventhub.event.EventRepository;
import com.achraf.eventhub.user.User;
import com.achraf.eventhub.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @GetMapping
    public ResponseEntity<Page<BookingResponseDto>> getAllBookings(Pageable pageable) {
        Page<BookingResponseDto> bookings = bookingService.getAllBookings(pageable)
                .map(bookingMapper::toResponseDto);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Integer id) {
        return bookingService.getBookingById(id)
                .map(bookingMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Booking savedBooking = bookingService.createBooking(bookingMapper.toEntity(dto, user, event));
        return ResponseEntity.ok(bookingMapper.toResponseDto(savedBooking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDto> updateBooking(
            @PathVariable Integer id,
            @Valid @RequestBody BookingDto dto
    ) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Booking updatedBooking = bookingMapper.toEntity(dto, user, event);
        Booking savedBooking = bookingService.updateBooking(id, updatedBooking);
        return ResponseEntity.ok(bookingMapper.toResponseDto(savedBooking));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingResponseDto> patchBooking(
            @PathVariable Integer id,
            @RequestBody BookingDto dto
    ) {
        User user = null;
        if (dto.userId() != null) {
            user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }

        Event event = null;
        if (dto.eventId() != null) {
            event = eventRepository.findById(dto.eventId())
                    .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        }

        Booking partialBooking = bookingMapper.toEntity(dto, user, event);
        Booking savedBooking = bookingService.patchBooking(id, partialBooking);
        return ResponseEntity.ok(bookingMapper.toResponseDto(savedBooking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    // 🔎 Filtering endpoints
    @GetMapping("/search/user")
    public ResponseEntity<Page<BookingResponseDto>> getBookingsByUser(
            @RequestParam Integer userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                bookingService.findByUserId(userId, pageable).map(bookingMapper::toResponseDto)
        );
    }

    @GetMapping("/search/event")
    public ResponseEntity<Page<BookingResponseDto>> getBookingsByEvent(
            @RequestParam Integer eventId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                bookingService.findByEventId(eventId, pageable).map(bookingMapper::toResponseDto)
        );
    }
}
