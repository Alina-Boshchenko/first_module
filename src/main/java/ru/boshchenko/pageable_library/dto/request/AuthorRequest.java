package ru.boshchenko.pageable_library.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthorRequest {

    @NotBlank(message = "first_name is required")
    private String firstName;

    @NotBlank(message = "last_name is required")
    private String lastName;

    @Email(message = "invalid email format")
    @NotBlank(message = "email is required")
    private String email;

}
