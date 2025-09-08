package com.achraf.eventhub.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(userMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(userMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(userMapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserDto dto) {
        User savedUser = userService.createUser(userMapper.toEntity(dto));
        return ResponseEntity.ok(userMapper.toResponseDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserDto dto
    ) {
        User updatedUser = userService.updateUser(id, userMapper.toEntity(dto));
        return ResponseEntity.ok(userMapper.toResponseDto(updatedUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> patchUser(
            @PathVariable Integer id,
            @RequestBody UserDto dto
    ) {
        User patchedUser = userService.patchUser(id, userMapper.toEntity(dto));
        return ResponseEntity.ok(userMapper.toResponseDto(patchedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Paginated + optional filtering by role
    @GetMapping("/page")
    public ResponseEntity<Page<UserResponseDto>> getUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Role role
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage =
                (role != null) ? userService.getUsersByRole(role, pageable)
                        : userService.getAllUsers(pageable);

        Page<UserResponseDto> dtoPage = usersPage.map(userMapper::toResponseDto);
        return ResponseEntity.ok(dtoPage);
    }
}
