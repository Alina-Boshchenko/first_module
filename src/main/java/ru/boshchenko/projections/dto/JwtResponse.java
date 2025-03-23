package ru.boshchenko.projections.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;
import ru.boshchenko.projections.model.Role;

import java.util.Set;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtResponse {
    String token;
}