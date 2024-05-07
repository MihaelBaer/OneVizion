package com.trial.onevizion.infrastructure.repository;

import com.trial.onevizion.domain.models.Book;
import com.trial.onevizion.infrastructure.repository.abstraction.BookRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setDescription(rs.getString("description"));
        return book;
    };

    @Override
    public List<Book> findAll() {
        String sql = "SELECT id, title, author, description FROM book";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public List<Book> findAllByOrderByTitleDesc() {
        String sql = "SELECT id, title, author, description FROM book ORDER BY title DESC";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public Book save(Book book) {
        String insertSql = "INSERT INTO book (title, author, description) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            book.setId(key.longValue());
        }

        return book;
    }
}
