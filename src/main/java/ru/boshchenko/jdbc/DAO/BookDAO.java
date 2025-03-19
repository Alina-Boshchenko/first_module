package ru.boshchenko.jdbc.DAO;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.boshchenko.jdbc.model.Book;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        createTable();
    }

    public void createTable() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS books (
                    id UUID default random_uuid() PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    author VARCHAR(255) NOT NULL,
                    publication_date DATE NOT NULL
                )
                """);
    }

    public UUID save(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connect -> {
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "insert into books (title,author,publication_date) values (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setObject(3, book.getPublicationDate());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKeyAs(UUID.class);
    }

    public List<Book> findAll() {
        return jdbcTemplate.query("select * from books", new BeanPropertyRowMapper<>(Book.class));
    }

    public void deleteById(UUID id) {
        jdbcTemplate.update("delete from books where id = (?)", id);
    }

    public Book findById(UUID id) {
        return jdbcTemplate.queryForObject("select * from books where id = (?)",
                new BeanPropertyRowMapper<>(Book.class), id);
    }

    public int update(UUID id, Book book) {
        List<String> updates = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (book.getTitle() != null) {
            updates.add("title = ?");
            params.add(book.getTitle());
        }
        if (book.getAuthor() != null) {
            updates.add("author = ?");
            params.add(book.getAuthor());
        }
        if (book.getPublicationDate() != null) {
            updates.add("publication_date = ?");
            params.add(book.getPublicationDate());
        }
        if (updates.isEmpty()) return 0;
        String sql = "UPDATE books SET "
                + String.join(", ", updates)
                + " WHERE id = ?";
        params.add(id);
        return jdbcTemplate.update(sql, params.toArray());
    }
}
