package com.trial.onevizion.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trial.onevizion.application.abstraction.BookService;
import com.trial.onevizion.presentation.dto.BookCreationDto;
import com.trial.onevizion.presentation.dto.BookRetrievalDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks_ReturnsBooks() throws Exception {
        BookRetrievalDto book1 = new BookRetrievalDto();
        book1.setTitle("Effective Java");
        BookRetrievalDto book2 = new BookRetrievalDto();
        book2.setTitle("Java Concurrency in Practice");

        List<BookRetrievalDto> books = Arrays.asList(book1, book2);
        when(bookService.findAllBooksSortedDescByTitle()).thenReturn(books);

        mockMvc.perform(get("/books/desc")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Effective Java")))
                .andExpect(jsonPath("$[1].title", is("Java Concurrency in Practice")));

        verify(bookService).findAllBooksSortedDescByTitle();
    }

    @Test
    void getAllBooks_ReturnsServerError() throws Exception {
        when(bookService.findAllBooksSortedDescByTitle()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/books/desc")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(bookService).findAllBooksSortedDescByTitle();
    }

    @Test
    void getBooksGroupedByAuthor_ReturnsBooksGrouped() throws Exception {
        Map<String, List<BookRetrievalDto>> groupedBooks = new HashMap<>();
        groupedBooks.put("Author1", List.of(
                new BookRetrievalDto(1L, "Book1", "Author 1", "Description 1"),
                new BookRetrievalDto(2L, "Book2", "Author 2", "Description 2")));
        groupedBooks.put("Author2", List.of(new BookRetrievalDto(3L,"Book3", "Author 3", "Description 3")));

        when(bookService.findBooksGroupedByAuthor()).thenReturn(groupedBooks);

        mockMvc.perform(get("/books/by-author")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Author1[0].title").value("Book1"))
                .andExpect(jsonPath("$.Author2[0].title").value("Book3"));

        verify(bookService).findBooksGroupedByAuthor();
    }

    @Test
    void getBooksGroupedByAuthor_ReturnsInternalServerError() throws Exception {
        when(bookService.findBooksGroupedByAuthor()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/books/by-author")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(bookService).findBooksGroupedByAuthor();
    }

    @Test
    void getTopAuthorsByCharOccurrence_ReturnsTopAuthors() throws Exception {
        when(bookService.findTopAuthorsByCharacterOccurrence("a")).thenReturn(List.of(
                new AbstractMap.SimpleEntry<>("Author1", 5),
                new AbstractMap.SimpleEntry<>("Author2", 3)
                                                                                     ));

        mockMvc.perform(get("/books/top-char-occurrences")
                                .param("char", "a")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].Author1").value(5))
                .andExpect(jsonPath("$[1].Author2").value(3));

        verify(bookService).findTopAuthorsByCharacterOccurrence("a");
    }

    @Test
    void getTopAuthorsByCharOccurrence_ThrowsInternalServerError() throws Exception {
        when(bookService.findTopAuthorsByCharacterOccurrence("b")).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/books/top-char-occurrences")
                                .param("char", "b")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(bookService).findTopAuthorsByCharacterOccurrence("b");
    }

    @Test
    void addBook_ReturnsCreatedBook() throws Exception {
        BookCreationDto newBookDto = new BookCreationDto("New Book", "Author Name", "Description of the book");
        BookCreationDto createdBookDto = new BookCreationDto("New Book", "Author Name", "Description of the book");

        when(bookService.addBook(any(BookCreationDto.class))).thenReturn(createdBookDto);

        mockMvc.perform(post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newBookDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(newBookDto.getTitle()));

        verify(bookService).addBook(any(BookCreationDto.class));
    }

    @Test
    void addBook_InternalServerError() throws Exception {
        BookCreationDto newBookDto = new BookCreationDto("New Book", "Author Name", "Description of the book");

        when(bookService.addBook(any(BookCreationDto.class))).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newBookDto)))
                .andExpect(status().isInternalServerError());

        verify(bookService).addBook(any(BookCreationDto.class));
    }

    @Test
    void addBook_IllegalArgumentException() throws Exception {
        BookCreationDto newBookDto = new BookCreationDto("New Book", "Author Name", "Description of the book");

        when(bookService.addBook(any(BookCreationDto.class))).thenThrow(new IllegalArgumentException("Invalid Argument"));

        mockMvc.perform(post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(newBookDto)))
                .andExpect(status().isBadRequest());

        verify(bookService).addBook(any(BookCreationDto.class));
    }

    @Test
    void whenPostRequestToBooksAndInvalidFields_thenCorrectResponse() throws Exception {
        BookCreationDto newBookDto = new BookCreationDto("", "", "Description of the book");

        String bookJson = objectMapper.writeValueAsString(newBookDto);

        mockMvc.perform(post("/books")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title must not be blank"))
                .andExpect(jsonPath("$.author").value("Author must not be blank"));
    }
}