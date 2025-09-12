package com.achraf.eventhub.user;

import com.achraf.eventhub.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserMapper userMapper;

    record AuthRequest(String username, String password) {}

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserDto dto) {
        // basic checks (exists)
        if (userService.getUserByUsername(dto.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userService.getUserByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(dto);
        // createUser will encode the password
        User created = userService.createUser(user);
        return ResponseEntity.ok(userMapper.toResponseDto(created));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
        var token = authenticateAndGenerateToken(request.username(), request.password());
        return ResponseEntity.ok(Map.of("token", token));
    }

    private String authenticateAndGenerateToken(String username, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication); // will throw if bad credentials
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails.getUser());
    }
}
