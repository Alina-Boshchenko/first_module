package ru.boshchenko.pageable_library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.boshchenko.pageable_library.dto.request.BookRequest;
import ru.boshchenko.pageable_library.dto.request.BookRequestPatch;
import ru.boshchenko.pageable_library.dto.response.BookResponse;
import ru.boshchenko.pageable_library.dto.response.PagedDataResponse;
import ru.boshchenko.pageable_library.exception.ResourceNotFoundException;
import ru.boshchenko.pageable_library.mapper.BookMapper;
import ru.boshchenko.pageable_library.model.Author;
import ru.boshchenko.pageable_library.model.Book;
import ru.boshchenko.pageable_library.repo.AuthorRepository;
import ru.boshchenko.pageable_library.repo.BookRepository;
import ru.boshchenko.pageable_library.service.BookService;

import java.util.List;
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
        bookResponse.setAuthorName(author.getFirstName() + author.getLastName());
        return bookResponse;
    }

    @Override
    public PagedDataResponse<BookResponse> findAll(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        String properties = sortParams[0];
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);

        Pageable pageable = PageRequest.of(page, size, direction, properties);
        Page<Book> sheet = bookRepository.findAll(pageable);

        List<BookResponse> responseList = sheet.getContent().stream()
                .map(el -> {
                    String authorName = el.getAuthor().getLastName().concat(" " + el.getAuthor().getFirstName());
                    BookResponse response = bookMapper.toBookResponse(el);
                    response.setAuthorName(authorName);
                    return response;
                })
                .toList();

        PagedDataResponse<BookResponse> pagedData = new PagedDataResponse<>();
        pagedData.setData(responseList);
        pagedData.setTotal(sheet.getTotalElements());
        return pagedData;
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
}
