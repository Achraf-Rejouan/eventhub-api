package com.achraf.eventhub.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findByUser_Id(Integer userId, Pageable pageable);
    Page<Booking> findByEvent_Id(Integer eventId, Pageable pageable);
}
