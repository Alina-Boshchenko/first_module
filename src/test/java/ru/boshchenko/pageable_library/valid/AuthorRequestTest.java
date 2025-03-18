package ru.boshchenko.pageable_library.valid;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ru.boshchenko.pageable_library.dto.request.AuthorRequest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthorRequestTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    private AuthorRequest authorRequest;

    @BeforeEach
    public void setUp(){
        authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Anna");
        authorRequest.setLastName("Golub");
        authorRequest.setEmail("anna@gmail.com");
    }

    @Test
    public void testValidAuthorRequest(){
        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(authorRequest);
        assertEquals(0, violations.size());
    }

    @Test
    public void testEmptyFirstName() {
        authorRequest.setFirstName("");
        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(authorRequest);
        assertEquals(1, violations.size());
        assertEquals("first_name is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullLastName() {
        authorRequest.setLastName(null);
        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(authorRequest);
        assertEquals(1, violations.size());
        assertEquals("last_name is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidEmail() {
        authorRequest.setEmail("invalid-email");
        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(authorRequest);
        assertEquals(1, violations.size());
        assertEquals("invalid email format", violations.iterator().next().getMessage());
    }

    @Test
    public void testEmptyEmail() {
        authorRequest.setEmail("");
        Set<ConstraintViolation<AuthorRequest>> violations = validator.validate(authorRequest);
        assertEquals(1, violations.size());
        assertEquals("email is required", violations.iterator().next().getMessage());
    }



}
