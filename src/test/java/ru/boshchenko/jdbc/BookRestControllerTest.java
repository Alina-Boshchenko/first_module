package ru.boshchenko.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.boshchenko.jdbc.controller.BookRestController;
import ru.boshchenko.jdbc.exception.NotFoundOrUpdateNotSuccessfulException;
import ru.boshchenko.jdbc.model.Book;
import ru.boshchenko.jdbc.service.BookService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private final UUID testId = UUID.randomUUID();
    private final Book testBook = new Book(testId, "Test", "Author", LocalDate.of(2023,5,10));

    @Test
    void getAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.singletonList(testBook));

        mockMvc.perform(get("/api/book/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].title").value("Test"));

        verify(bookService, times(1)).findAll();
    }

    @Test
    void createBook() throws Exception {
        when(bookService.create(any(Book.class))).thenReturn(testId);

        mockMvc.perform(post("/api/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test\",\"author\":\"Author\",\"publicationDate\":\"2023-05-03\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(testId.toString()));
        verify(bookService, times(1)).create(any(Book.class));
    }

    @Test
    void getBookById() throws Exception {
        when(bookService.findById(testId)).thenReturn(testBook);

        mockMvc.perform(get("/api/book/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.title").value("Test"));
        verify(bookService, times(1)).findById(testId);
    }

    @Test
    void updateBook() throws Exception {
        doNothing().when(bookService).update(eq(testId), any(Book.class));

        mockMvc.perform(patch("/api/book/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Title\"}"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("update"));
        verify(bookService, times(1)).update(eq(testId), any(Book.class));
    }

    @Test
    void deleteBook() throws Exception {
        doNothing().when(bookService).deleteById(testId);

        mockMvc.perform(delete("/api/book/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(content().string("delete"));
        verify(bookService, times(1)).deleteById(testId);
    }

    @Test
    void getNonExistingBook() throws Exception {
        when(bookService.findById(testId)).thenThrow(new NotFoundOrUpdateNotSuccessfulException("saved process bed"));
        mockMvc.perform(get("/api/book/{id}", testId))
                .andExpect(status().isNotFound());
    }
}
