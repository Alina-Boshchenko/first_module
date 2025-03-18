package ru.boshchenko.pageable_library.service;

import org.springframework.stereotype.Service;
import ru.boshchenko.pageable_library.dto.request.BookRequest;
import ru.boshchenko.pageable_library.dto.request.BookRequestPatch;
import ru.boshchenko.pageable_library.dto.response.BookResponse;
import ru.boshchenko.pageable_library.dto.response.PagedDataResponse;

import java.util.UUID;

@Service
public interface BookService {

    BookResponse create(BookRequest bookRequest);

    PagedDataResponse<BookResponse> findAll(int page, int size, String sort);

    BookResponse findById(UUID id);

    BookResponse update(UUID id, BookRequestPatch bookRequest);

    void deleteById(UUID id);

}
