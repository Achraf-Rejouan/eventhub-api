package com.achraf.eventhub.event;

import com.achraf.eventhub.user.User;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toEntity(EventDto dto, User organizer) {
        if (dto == null) return null;
        return Event.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .date(dto.date())
                .location(dto.location())
                .availableSeats(dto.availableSeats())
                .price(dto.price())
                .organizer(organizer)
                .build();
    }

    public EventResponseDto toResponseDto(Event event) {
        if (event == null) return null;
        return new EventResponseDto(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getAvailableSeats(),
                event.getPrice()
        );
    }
}
