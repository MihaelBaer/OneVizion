package com.trial.onevizion.infrastructure.repository.abstraction;

import com.trial.onevizion.domain.models.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();

    List<Book> findAllByOrderByTitleDesc();

    Book save(Book book);
}
