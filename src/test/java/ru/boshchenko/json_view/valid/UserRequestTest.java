package ru.boshchenko.json_view.valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.ConstraintViolation;
import ru.boshchenko.json_view.dto.request.UserRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRequestTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    private UserRequest userRequest;

    @BeforeEach
    public void setUp() {
        userRequest = new UserRequest();
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setEmail("john.doe@example.com");
    }

    @Test
    public void testValidUserRequest() {
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidEmail() {
        userRequest.setEmail("invalid-email");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertEquals(1, violations.size());
        assertEquals("invalid email format", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmptyFirstName() {
        userRequest.setFirstName("");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertEquals(1, violations.size());
        assertEquals("firstName is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullLastName() {
        userRequest.setLastName(null);
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertEquals(1, violations.size());
        assertEquals("lastName is required", violations.iterator().next().getMessage());
    }
}