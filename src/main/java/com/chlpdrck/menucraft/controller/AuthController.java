package com.chlpdrck.menucraft.controller;

import com.chlpdrck.menucraft.entity.Role;
import com.chlpdrck.menucraft.entity.User;
import com.chlpdrck.menucraft.mapper.dto.UserAuthDto;
import com.chlpdrck.menucraft.repository.UserRepository;
import com.chlpdrck.menucraft.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserAuthDto userAuthDto) {
        if (userRepository.findByUsername(userAuthDto.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", "Пользователь с таким именем уже существует"));
        }

        User user = new User();
        user.setUsername(userAuthDto.getUsername());
        user.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return ResponseEntity.ok(Collections.singletonMap("message", "Пользователь зарегистрирован"));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserAuthDto userAuthDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAuthDto.getUsername(), userAuthDto.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userAuthDto.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String oldToken = request.get("token");
        if (oldToken == null || !jwtUtil.validateToken(oldToken)) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Токен недействителен или истёк"));
        }

        String username = jwtUtil.extractUsername(oldToken);
        String newToken = jwtUtil.generateToken(userDetailsService.loadUserByUsername(username));

        return ResponseEntity.ok(Collections.singletonMap("token", newToken));
    }
}