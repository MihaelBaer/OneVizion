package com.trial.onevizion.application.service;

import com.trial.onevizion.application.mapper.BookMapper;
import com.trial.onevizion.domain.models.Book;
import com.trial.onevizion.infrastructure.repository.abstraction.BookRepository;
import com.trial.onevizion.presentation.dto.BookCreationDto;
import com.trial.onevizion.presentation.dto.BookRetrievalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;
    private BookRetrievalDto bookRetrievalDto1;
    private BookRetrievalDto bookRetrievalDto2;
    private BookCreationDto bookCreationDto;

    @BeforeEach
    void setUp() {
        book1 = new Book(1L, "Test book 1", "Test Author 1", "Test description 1");
        book2 = new Book(2L, "Test book 2", "Test Author 2", "Test desssscription 2");
        bookRetrievalDto1 = new BookRetrievalDto();
        bookRetrievalDto2 = new BookRetrievalDto();
        bookCreationDto = new BookCreationDto();
    }

    @Test
    void findAllBooksSortedDescByTitle() {
        List<Book> books = List.of(book1, book2);

        when(bookRepository.findAllByOrderByTitleDesc()).thenReturn(books);
        when(bookMapper.bookToBookRetreivalDto(book1)).thenReturn(bookRetrievalDto1);
        when(bookMapper.bookToBookRetreivalDto(book2)).thenReturn(bookRetrievalDto2);

        List<BookRetrievalDto> results = bookService.findAllBooksSortedDescByTitle();

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(bookRetrievalDto2, results.get(0));
        assertEquals(bookRetrievalDto1, results.get(1));
        verify(bookRepository).findAllByOrderByTitleDesc();
        verify(bookMapper, times(2)).bookToBookRetreivalDto(any());
    }

    @Test
    void addBook() {
        when(bookMapper.bookRoutingDtoToBook(any())).thenReturn(book1);
        when(bookRepository.save(any())).thenReturn(book1);
        when(bookMapper.bookToBookCreationDto(any())).thenReturn(bookCreationDto);

        BookCreationDto result = bookService.addBook(bookCreationDto);

        assertNotNull(result);
        assertEquals(bookCreationDto, result);
        verify(bookMapper).bookRoutingDtoToBook(any());
        verify(bookRepository).save(any());
        verify(bookMapper).bookToBookCreationDto(any());
    }

    @Test
    void findBooksGroupedByAuthor() {
        List<Book> books = List.of(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.bookToBookRetreivalDto(book1)).thenReturn(bookRetrievalDto1);
        when(bookMapper.bookToBookRetreivalDto(book2)).thenReturn(bookRetrievalDto2);

        Map<String, List<BookRetrievalDto>> results = bookService.findBooksGroupedByAuthor();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertTrue(results.containsKey(book1.getAuthor()));
        assertTrue(results.containsKey(book2.getAuthor()));
        verify(bookRepository).findAll();
        verify(bookMapper, times(2)).bookToBookRetreivalDto(any());
    }

    @Test
    void findTopAuthorsByCharacterOccurrence() {
        List<Book> books = List.of(book1, book2);
        String character = "s";

        when(bookRepository.findAll()).thenReturn(books);

        List<Map.Entry<String, Integer>> results = bookService.findTopAuthorsByCharacterOccurrence(character);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
        assertTrue(results.get(0).getValue() >= results.get(1).getValue());
        verify(bookRepository).findAll();
    }
}