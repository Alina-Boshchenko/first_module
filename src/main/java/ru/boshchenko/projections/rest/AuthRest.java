package ru.boshchenko.projections.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.boshchenko.projections.dto.JwtResponse;
import ru.boshchenko.projections.dto.LoginRequest;
import ru.boshchenko.projections.security.jwt.JwtUtils;
import ru.boshchenko.projections.security.jwt.LoginAttemptService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRest {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final LoginAttemptService loginAttemptService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateToken(userDetails);

            loginAttemptService.loginSuccess(request.getUsername());

            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (BadCredentialsException e) {
            loginAttemptService.loginFailed(request.getUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
}
