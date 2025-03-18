package ru.boshchenko.pageable_library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.boshchenko.pageable_library.controller.AuthorRestController;
import ru.boshchenko.pageable_library.dto.request.AuthorRequest;
import ru.boshchenko.pageable_library.dto.response.AuthorResponse;
import ru.boshchenko.pageable_library.dto.response.BookResponse;
import ru.boshchenko.pageable_library.service.AuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorRestController.class)
class AuthorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    private AuthorRequest authorRequest;

    @BeforeEach
    public void setUp() {
        authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Anna");
        authorRequest.setLastName("Golub");
        authorRequest.setEmail("anna@gmail.com");
    }

    @Test
    void createAuthorValidRequestReturnsCreated() throws Exception {
        List<BookResponse> bookResponses = new ArrayList<>();
        AuthorResponse response =
                new AuthorResponse(UUID.randomUUID(), "Anna", "Golub",
                        "anna@gmail.com", bookResponses);

        Mockito.when(authorService.create(authorRequest)).thenReturn(response);

        mockMvc.perform(post("/api/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name").value("Anna"))
                .andExpect(jsonPath("$.last_name").value("Golub"))
                .andExpect(jsonPath("$.email").value("anna@gmail.com"))
                .andExpect(jsonPath("$.book_responses").value(bookResponses));
    }
}