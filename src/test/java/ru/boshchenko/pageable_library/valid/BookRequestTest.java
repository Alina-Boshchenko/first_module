package ru.boshchenko.pageable_library.valid;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import ru.boshchenko.pageable_library.dto.request.BookRequest;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookRequestTest {

    @Autowired
    private LocalValidatorFactoryBean validator;

    private BookRequest bookRequest;

    @BeforeEach
    public void setUp(){
        bookRequest = new BookRequest();
        bookRequest.setTitle("titleBook");
        bookRequest.setPublicationDate(LocalDate.now().minusDays(1));
        bookRequest.setAuthorId(UUID.randomUUID());
    }

    @Test
    public void testValidBookRequest(){
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(bookRequest);
        assertEquals(0, violations.size());
    }

    @Test
    public void testEmptyTitle(){
        bookRequest.setTitle("");
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(bookRequest);
        assertEquals(1, violations.size());
        assertEquals("title is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullTitle(){
        bookRequest.setTitle(null);
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(bookRequest);
        assertEquals(1, violations.size());
        assertEquals("title is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullPublicationDate(){
        bookRequest.setPublicationDate(null);
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(bookRequest);
        assertEquals(1, violations.size());
        assertEquals("publication date is required", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidPublicationDate(){
        bookRequest.setPublicationDate(LocalDate.now().plusMonths(1));
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(bookRequest);
        assertEquals(1, violations.size());
        assertEquals("publication date must be in the past or present", violations.iterator().next().getMessage());
    }

    @Test
    public void testNullAuthorId(){
        bookRequest.setAuthorId(null);
        Set<ConstraintViolation<BookRequest>> violations = validator.validate(bookRequest);
        assertEquals(1, violations.size());
        assertEquals("author_id is required", violations.iterator().next().getMessage());
    }


}
