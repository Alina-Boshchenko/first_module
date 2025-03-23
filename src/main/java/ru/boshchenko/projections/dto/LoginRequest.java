package ru.boshchenko.projections.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {
    String username;
    String password;
}