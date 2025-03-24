package ru.boshchenko.projections.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.boshchenko.projections.dto.JwtResponse;
import ru.boshchenko.projections.exception.AuthException;
import ru.boshchenko.projections.exception.GlobalExceptionHandler;
import ru.boshchenko.projections.exception.ResourceNotFoundException;

import ru.boshchenko.projections.rest.AuthRest;
import ru.boshchenko.projections.service.inter.AuthService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthRestSecurityTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthRest authRest;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authRest)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testSuccessfulAuthentication() throws Exception {
        JwtResponse response = new JwtResponse("test.jwt.token");
        when(authService.authenticate(anyString(), anyString())).thenReturn(response);

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test.jwt.token"));
    }

    @Test
    public void testAuthenticationWithInvalidCredentials() throws Exception {
        when(authService.authenticate(anyString(), anyString())).thenThrow(new AuthException("Invalid credentials", new BadCredentialsException("Invalid credentials")));

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid credentials"));
    }

    @Test
    public void testAuthenticationForLockedAccount() throws Exception {
        when(authService.authenticate(anyString(), anyString())).thenThrow(new AuthException("User account is locked", new LockedException("Account locked")));

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user\",\"password\":\"password\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("User account is locked"));
    }

    @Test
    public void testAuthenticationForNonExistentUser() throws Exception {
        when(authService.authenticate(anyString(), anyString()))
                .thenThrow(new AuthException("User not found", new ResourceNotFoundException("User not found")));
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"unknown\",\"password\":\"password\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("User not found"));
    }
}