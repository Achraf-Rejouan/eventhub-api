package com.achraf.eventhub.user;

public record UserResponseDto(
        Integer id,
        String username,
        String email,
        Role role
) {}
