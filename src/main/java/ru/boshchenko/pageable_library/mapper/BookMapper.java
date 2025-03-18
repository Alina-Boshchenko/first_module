package ru.boshchenko.pageable_library.mapper;

import ru.boshchenko.pageable_library.dto.request.BookRequest;
import ru.boshchenko.pageable_library.dto.request.BookRequestPatch;
import ru.boshchenko.pageable_library.dto.response.BookResponse;
import ru.boshchenko.pageable_library.model.Book;






public class BookMapper {

    public Book toBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setPublicationDate(bookRequest.getPublicationDate());
        return book;
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .publicationDate(book.getPublicationDate())
                .build();
    }

    public void updateBookFromRequest(BookRequestPatch bookRequest, Book book) {
        if (bookRequest.getTitle() != null) {
            book.setTitle(bookRequest.getTitle());
        }
        if (bookRequest.getPublicationDate() != null) {
            book.setPublicationDate(bookRequest.getPublicationDate());
        }
    }

}
