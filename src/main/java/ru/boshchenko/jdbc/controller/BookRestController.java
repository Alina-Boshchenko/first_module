package ru.boshchenko.jdbc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.jdbc.model.Book;
import ru.boshchenko.jdbc.service.BookService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody Book book) {
        UUID id = bookService.create(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(id.toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestBody Book book) {
        bookService.update(id, book);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        bookService.deleteById(id);
        return ResponseEntity.ok("delete");
    }

}
