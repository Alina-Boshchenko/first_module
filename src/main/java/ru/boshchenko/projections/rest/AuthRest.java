package ru.boshchenko.projections.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.boshchenko.projections.dto.JwtResponse;
import ru.boshchenko.projections.dto.LoginRequest;
import ru.boshchenko.projections.security.jwt.JwtUtils;
import ru.boshchenko.projections.security.jwt.LoginAttemptService;
import ru.boshchenko.projections.service.inter.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthRest {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(
                request.getUsername(),
                request.getPassword()
        ));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> authUser(){
        return ResponseEntity.ok("user access");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> authAdmin(){
        return ResponseEntity.ok("admin access");
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<String> authModerator(){
        return ResponseEntity.ok("moderator access");
    }
}
