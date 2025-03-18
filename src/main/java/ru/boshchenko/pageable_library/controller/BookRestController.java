package ru.boshchenko.pageable_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.pageable_library.dto.request.BookRequest;
import ru.boshchenko.pageable_library.dto.request.BookRequestPatch;
import ru.boshchenko.pageable_library.dto.response.BookResponse;
import ru.boshchenko.pageable_library.dto.response.PagedDataResponse;
import ru.boshchenko.pageable_library.service.BookService;

import java.util.UUID;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    //http://localhost:8080/api/book/all?page=100&size=10&sort=publicationDate,asc
    //http://localhost:8080/api/book/all?page=100&size=10&sort=title,asc
    @GetMapping("/all")
    public ResponseEntity<PagedDataResponse<BookResponse>> getPageAndSort(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id,asc") String sort
    ) {
        PagedDataResponse<BookResponse> data = bookService.findAll(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @PostMapping("/create")
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest bookRequest) {
        BookResponse bookResponse = bookService.create(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getAuthor(@PathVariable UUID id) {
        BookResponse bookResponse = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable UUID id, @Valid @RequestBody BookRequestPatch bookRequest) {
        BookResponse bookResponse = bookService.update(id, bookRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        bookService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

}
