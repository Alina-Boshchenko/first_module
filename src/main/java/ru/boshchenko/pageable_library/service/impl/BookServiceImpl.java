package ru.boshchenko.pageable_library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.boshchenko.pageable_library.dto.request.BookRequest;
import ru.boshchenko.pageable_library.dto.request.BookRequestPatch;
import ru.boshchenko.pageable_library.dto.response.BookResponse;
import ru.boshchenko.pageable_library.exception.ResourceNotFoundException;
import ru.boshchenko.pageable_library.mapper.BookMapper;
import ru.boshchenko.pageable_library.model.Author;
import ru.boshchenko.pageable_library.model.Book;
import ru.boshchenko.pageable_library.repo.AuthorRepository;
import ru.boshchenko.pageable_library.repo.BookRepository;
import ru.boshchenko.pageable_library.service.BookService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper = new BookMapper();
    private final AuthorRepository authorRepository;


    @Override
    public BookResponse create(BookRequest bookRequest) {
        Book book = bookMapper.toBook(bookRequest);
        Author author = authorRepository.findById(bookRequest.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        book.setAuthor(author);
        bookRepository.save(book);
        BookResponse bookResponse = bookMapper.toBookResponse(book);
        bookResponse.setAuthorName(formatAuthorName(author));
        return bookResponse;
    }

    @Override
    public Page<BookResponse> findAll(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        return page.map(el -> {
            BookResponse response = bookMapper.toBookResponse(el);
            response.setAuthorName(formatAuthorName(el.getAuthor()));
            return response;
        });
    }

    @Override
    public BookResponse findById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return bookMapper.toBookResponse(book);
    }

    @Override
    public BookResponse update(UUID id, BookRequestPatch bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookMapper.updateBookFromRequest(bookRequest, book);
        Book bookUpdate = bookRepository.save(book);
        return bookMapper.toBookResponse(bookUpdate);
    }

    @Override
    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }

    private String formatAuthorName(Author author) {
        return String.format("%s %s", author.getLastName(), author.getFirstName());
    }
}
