package com.achraf.eventhub.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public Page<Booking> getAllBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    public Optional<Booking> getBookingById(Integer id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Integer id, Booking updatedBooking) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        existing.setEvent(updatedBooking.getEvent());
        existing.setUser(updatedBooking.getUser());
        existing.setBookingDate(updatedBooking.getBookingDate());
        existing.setSeatsBooked(updatedBooking.getSeatsBooked());

        return bookingRepository.save(existing);
    }

    public Booking patchBooking(Integer id, Booking partialBooking) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (partialBooking.getEvent() != null) existing.setEvent(partialBooking.getEvent());
        if (partialBooking.getUser() != null) existing.setUser(partialBooking.getUser());
        if (partialBooking.getBookingDate() != null) existing.setBookingDate(partialBooking.getBookingDate());
        if (partialBooking.getSeatsBooked() != null) existing.setSeatsBooked(partialBooking.getSeatsBooked());

        return bookingRepository.save(existing);
    }

    public void deleteBooking(Integer id) {
        bookingRepository.deleteById(id);
    }

    // 🔎 Filtering
    public Page<Booking> findByUserId(Integer userId, Pageable pageable) {
        return bookingRepository.findByUser_Id(userId, pageable);
    }

    public Page<Booking> findByEventId(Integer eventId, Pageable pageable) {
        return bookingRepository.findByEvent_Id(eventId, pageable);
    }
}
