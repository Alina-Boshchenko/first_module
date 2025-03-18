package ru.boshchenko.pageable_library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.boshchenko.pageable_library.controller.BookRestController;
import ru.boshchenko.pageable_library.dto.request.BookRequest;
import ru.boshchenko.pageable_library.dto.request.BookRequestPatch;
import ru.boshchenko.pageable_library.dto.response.BookResponse;
import ru.boshchenko.pageable_library.dto.response.PagedDataResponse;
import ru.boshchenko.pageable_library.exception.ResourceNotFoundException;
import ru.boshchenko.pageable_library.repo.AuthorRepository;
import ru.boshchenko.pageable_library.service.BookService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    private final UUID bookId = UUID.randomUUID();
    private final UUID authorId = UUID.randomUUID();
    private final LocalDate pubDate = LocalDate.now().minusYears(1);

    @Test
    void getBooksWithPagination() throws Exception {
        BookResponse response = new BookResponse();
        response.setId(bookId);
        response.setTitle("title");
        response.setPublicationDate(pubDate);
        response.setAuthorName("Anna Golub");

        Page<BookResponse> page = new PageImpl<>(List.of(response));
        Mockito.when(bookService.findAll(0, 10, "id,asc"))
                .thenReturn(new PagedDataResponse<>(List.of(response), 1L));

        mockMvc.perform(get("/api/book/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("title"))
                .andExpect(jsonPath("$.data[0].author_name").value("Anna Golub"))
                .andExpect(jsonPath("$.total").value(1));
    }

    @Test
    void createBookWithValidAuthor() throws Exception {
        BookRequest request = new BookRequest("title", pubDate, authorId);
        BookResponse response = new BookResponse();
        response.setId(bookId);
        response.setAuthorName("Anna Golub");

        Mockito.when(bookService.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author_name").value("Anna Golub"))
                .andExpect(jsonPath("$.id").value(bookId.toString()));
    }

    @Test
    void createBookWithInvalidAuthor() throws Exception {
        BookRequest request = new BookRequest("title", pubDate, authorId);

        Mockito.when(bookService.create(any()))
                .thenThrow(new ResourceNotFoundException("Author not found"));

        mockMvc.perform(post("/api/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Author not found"));
    }

    @Test
    void getBookById() throws Exception {
        BookResponse response = new BookResponse();
        response.setId(bookId);
        response.setAuthorName("Anna Golub");

        Mockito.when(bookService.findById(bookId)).thenReturn(response);

        mockMvc.perform(get("/api/book/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author_name").value("Anna Golub"))
                .andExpect(jsonPath("$.id").value(bookId.toString()));
    }

    @Test
    void updateBook() throws Exception {
        BookRequestPatch request = new BookRequestPatch("New Title", null);
        BookResponse response = new BookResponse();
        response.setId(bookId);
        response.setTitle("New Title");
        response.setAuthorName("Anna Golub");

        Mockito.when(bookService.update(bookId, request)).thenReturn(response);

        mockMvc.perform(patch("/api/book/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.author_name").value("Anna Golub"));
    }

    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(delete("/api/book/{id}", bookId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNonExistingBook() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Book not found"))
                .when(bookService).deleteById(bookId);

        mockMvc.perform(delete("/api/book/{id}", bookId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }
}