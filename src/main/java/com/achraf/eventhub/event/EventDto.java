package com.achraf.eventhub.event;

import com.achraf.eventhub.user.UserDto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventDto {

    public static class CreateRequest {
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        private String title;

        @NotBlank(message = "Description is required")
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        private String description;

        @NotNull(message = "Date is required")
        @Future(message = "Event date must be in the future")
        private LocalDateTime date;

        @NotBlank(message = "Location is required")
        @Size(max = 200, message = "Location must not exceed 200 characters")
        private String location;

        @NotNull(message = "Available seats is required")
        @Min(value = 1, message = "Available seats must be at least 1")
        @Max(value = 10000, message = "Available seats must not exceed 10000")
        private Integer availableSeats;

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        private BigDecimal price;

        private String imageUrl;

        // Constructors
        public CreateRequest() {}

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public LocalDateTime getDate() { return date; }
        public void setDate(LocalDateTime date) { this.date = date; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public Integer getAvailableSeats() { return availableSeats; }
        public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }

    public static class UpdateRequest {
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        private String title;

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        private String description;

        @Future(message = "Event date must be in the future")
        private LocalDateTime date;

        @Size(max = 200, message = "Location must not exceed 200 characters")
        private String location;

        @Min(value = 1, message = "Available seats must be at least 1")
        @Max(value = 10000, message = "Available seats must not exceed 10000")
        private Integer availableSeats;

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        private BigDecimal price;

        private String imageUrl;

        // Constructors
        public UpdateRequest() {}

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public LocalDateTime getDate() { return date; }
        public void setDate(LocalDateTime date) { this.date = date; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public Integer getAvailableSeats() { return availableSeats; }
        public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }

    public static class Response {
        private Long id;
        private String title;
        private String description;
        private LocalDateTime date;
        private String location;
        private Integer availableSeats;
        private BigDecimal price;
        private String imageUrl;
        private UserDto.UserResponse organizer;

        // Constructors
        public Response() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public LocalDateTime getDate() { return date; }
        public void setDate(LocalDateTime date) { this.date = date; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public Integer getAvailableSeats() { return availableSeats; }
        public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public UserDto.UserResponse getOrganizer() { return organizer; }
        public void setOrganizer(UserDto.UserResponse organizer) { this.organizer = organizer; }
    }
}
