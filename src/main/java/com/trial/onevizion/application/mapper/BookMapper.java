package com.trial.onevizion.infrastructure.mapper;

import com.trial.onevizion.domain.Book;
import com.trial.onevizion.presentation.dto.BookCreationRoutingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    public abstract BookCreationRoutingDto bookToBookRoutingDto(Book book);

    public abstract Book bookRoutingDtoToBook(BookCreationRoutingDto bookCreationRoutingDto);
}
