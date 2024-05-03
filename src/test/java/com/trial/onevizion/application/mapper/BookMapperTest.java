package com.trial.onevizion.application;

import com.trial.onevizion.application.mapper.BookMapper;
import com.trial.onevizion.domain.models.Book;
import com.trial.onevizion.presentation.dto.BookCreationDto;
import com.trial.onevizion.presentation.dto.BookRetrievalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookMapperTest {

        private BookMapper bookMapper;

        @BeforeEach
        void setUp() {
            // Initialize the mapper
            bookMapper = Mappers.getMapper(BookMapper.class);
        }

        @Test
        void shouldMapBookToBookCreationDto() {
            Book book = new Book();
            book.setId(1L);
            book.setTitle("Effective Java");
            book.setAuthor("Joshua Bloch");
            book.setDescription("A programming book");

            BookCreationDto dto = bookMapper.bookToBookCreationDto(book);

            assertThat(dto).isNotNull();
            assertThat(dto.getTitle()).isEqualTo(book.getTitle());
            assertThat(dto.getAuthor()).isEqualTo(book.getAuthor());
            assertThat(dto.getDescription()).isEqualTo(book.getDescription());
        }

        @Test
        void shouldMapBookToBookRetrievalDto() {
            Book book = new Book();
            book.setId(1L);
            book.setTitle("Clean Code");
            book.setAuthor("Robert C. Martin");
            book.setDescription("A handbook of agile software craftsmanship");

            BookRetrievalDto dto = bookMapper.bookToBookRetreivalDto(book);

            assertThat(dto).isNotNull();
            assertThat(dto.getTitle()).isEqualTo(book.getTitle());
            assertThat(dto.getAuthor()).isEqualTo(book.getAuthor());
        }

        @Test
        void shouldMapBookCreationDtoToBook() {
            BookCreationDto dto = new BookCreationDto();
            dto.setTitle("The Pragmatic Programmer");
            dto.setAuthor("Andrew Hunt and David Thomas");
            dto.setDescription("From Journeyman to Master");

            Book book = bookMapper.bookRoutingDtoToBook(dto);

            assertThat(book).isNotNull();
            assertThat(book.getId()).isNull();
            assertThat(book.getTitle()).isEqualTo(dto.getTitle());
            assertThat(book.getAuthor()).isEqualTo(dto.getAuthor());
            assertThat(book.getDescription()).isEqualTo(dto.getDescription());
        }
}