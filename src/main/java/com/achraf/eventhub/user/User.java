package com.achraf.eventhub.user;

import com.achraf.eventhub.event.Event;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "username is required")
    @Size(min = 3, max = 50, message = "username length must be between 3 and 50")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String userName;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    @Size(max = 100, message = "email too long")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, max = 100, message = "password length must be between 6 and 100")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Event> events;
}
