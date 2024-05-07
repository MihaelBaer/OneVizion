package com.trial.onevizion.application.service;

import com.trial.onevizion.application.abstraction.BookService;
import com.trial.onevizion.domain.models.Book;
import com.trial.onevizion.application.mapper.BookMapper;
import com.trial.onevizion.infrastructure.repository.abstraction.BookRepository;
import com.trial.onevizion.presentation.dto.BookCreationDto;
import com.trial.onevizion.presentation.dto.BookRetrievalDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookRetrievalDto> findAllBooksSortedDescByTitle() {
        return bookRepository.findAllByOrderByTitleDesc().stream()
                .map(bookMapper::bookToBookRetreivalDto)
                .toList();
    }

    @Override
    @Transactional
    public BookCreationDto addBook(BookCreationDto newBook) {
        Book entity = bookMapper.bookRoutingDtoToBook(newBook);
        entity = bookRepository.save(entity);
        return bookMapper.bookToBookCreationDto(entity);
    }

    @Override
    public Map<String, List<BookRetrievalDto>> findBooksGroupedByAuthor() {
        return bookRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Book::getAuthor, Collectors.mapping(bookMapper::bookToBookRetreivalDto, Collectors.toList())));
    }

    @Override
    public List<Map.Entry<String, Integer>> findTopAuthorsByCharacterOccurrence(String character) {
        char targetChar = character.toLowerCase().charAt(0);

        return bookRepository.findAll().parallelStream()
                .map(book -> new AbstractMap.SimpleEntry<>(book.getAuthor(), (int) book.getTitle().toLowerCase().chars()
                                     .filter(ch -> ch == targetChar)
                                     .count()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .toList();
    }
}
