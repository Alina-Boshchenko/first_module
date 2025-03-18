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
import ru.boshchenko.pageable_library.exception.ResourceNotFoundException;
import ru.boshchenko.pageable_library.model.Author;
import ru.boshchenko.pageable_library.model.Book;
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
    private final LocalDate date = LocalDate.now().minusYears(1);

    @Test
    void getBooksWithPagination() throws Exception {
        Author author = new Author();
        author.setFirstName("Anna");
        author.setLastName("Golub");
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("title");
        book.setPublicationDate(date);
        book.setAuthor(author);

        Page<Book> entityPage = new PageImpl<>(List.of(book), PageRequest.of(0, 10), 1);
        Page<BookResponse> responsePage = entityPage.map(b -> {
            BookResponse res = new BookResponse();
            res.setId(b.getId());
            res.setTitle(b.getTitle());
            res.setPublicationDate(b.getPublicationDate());
            res.setAuthorName(b.getAuthor().getLastName() + " " + b.getAuthor().getFirstName());
            return res;
        });

        Mockito.when(bookService.findAll(any(Pageable.class))).thenReturn(responsePage);
        mockMvc.perform(get("/api/book/all")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "title,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("title"))
                .andExpect(jsonPath("$.content[0].author_name").value("Golub Anna"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.pageable.paged").value(true))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                .andExpect(jsonPath("$.pageable.pageSize").value(10));
    }

    @Test
    void createBookWithValidAuthor() throws Exception {
        BookRequest request = new BookRequest("title", date, authorId);
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
        BookRequest request = new BookRequest("title", date, authorId);

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