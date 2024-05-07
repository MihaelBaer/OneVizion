package com.trial.onevizion.application.abstraction;

import com.trial.onevizion.presentation.dto.BookCreationDto;
import com.trial.onevizion.presentation.dto.BookRetrievalDto;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<BookRetrievalDto> findAllBooksSortedDescByTitle();

    BookCreationDto addBook(BookCreationDto newBook);

    Map<String, List<BookRetrievalDto>> findBooksGroupedByAuthor();

    List<Map.Entry<String, Integer>> findTopAuthorsByCharacterOccurrence(String character);
}
