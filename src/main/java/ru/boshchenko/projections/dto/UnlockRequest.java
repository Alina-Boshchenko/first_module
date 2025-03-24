package ru.boshchenko.projections.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnlockRequest {
    @NotBlank(message = "username is not blank")
    String username;
}