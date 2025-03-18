package ru.boshchenko.pageable_library.mapper;

import ru.boshchenko.pageable_library.dto.request.AuthorRequest;
import ru.boshchenko.pageable_library.dto.response.BookResponse;
import ru.boshchenko.pageable_library.dto.response.AuthorResponse;
import ru.boshchenko.pageable_library.model.Author;

import java.util.List;

public class AuthorMapper {

    private final BookMapper bookMapper = new BookMapper();

    public Author toAuthor(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setFirstName(authorRequest.getFirstName());
        author.setLastName(authorRequest.getLastName());
        author.setEmail(authorRequest.getEmail());
        return author;
    }

    public AuthorResponse toResponse(Author author) {
        List<BookResponse> bookResponses = author
                .getBooks()
                .stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return AuthorResponse.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .email(author.getEmail())
                .bookResponses(bookResponses)
                .build();
    }

}
