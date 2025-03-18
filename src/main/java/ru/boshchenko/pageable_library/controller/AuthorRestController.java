package ru.boshchenko.pageable_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.pageable_library.dto.request.AuthorRequest;
import ru.boshchenko.pageable_library.dto.response.AuthorResponse;
import ru.boshchenko.pageable_library.service.AuthorService;

import java.util.UUID;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @PostMapping("/create")
    public ResponseEntity<AuthorResponse> create(@Valid @RequestBody AuthorRequest authorRequest) {
        AuthorResponse authorResponse = authorService.create(authorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthor(@PathVariable UUID id) {
        AuthorResponse authorResponse = authorService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(authorResponse);
    }

}
