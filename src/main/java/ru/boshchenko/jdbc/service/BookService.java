package ru.boshchenko.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.boshchenko.jdbc.DAO.BookDAO;
import ru.boshchenko.jdbc.exception.NotFoundOrUpdateNotSuccessfulException;
import ru.boshchenko.jdbc.model.Book;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDAO bookDAO;

    public UUID create(Book book) {
        UUID id = bookDAO.save(book);
        if (id == null) {
            throw new NotFoundOrUpdateNotSuccessfulException("saved process bed");
        }
        return id;
    }

    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    public Book findById(UUID id) {
        return bookDAO.findById(id);
    }

    public void deleteById(UUID id) {
        bookDAO.deleteById(id);
    }

    public void update(UUID id, Book book) {
        if (bookDAO.update(id, book) <= 0) {
            throw new NotFoundOrUpdateNotSuccessfulException("there is no resource or the update was not successful");
        }
    }

}
