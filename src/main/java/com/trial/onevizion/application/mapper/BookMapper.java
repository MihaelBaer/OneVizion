package com.trial.onevizion.application.mapper;

import com.trial.onevizion.domain.models.Book;
import com.trial.onevizion.presentation.dto.BookCreationDto;
import com.trial.onevizion.presentation.dto.BookRetrievalDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    public abstract BookCreationDto bookToBookCreationDto(Book book);

    public abstract BookRetrievalDto bookToBookRetreivalDto(Book book);

    @Mapping(target = "id", ignore = true)
    public abstract Book bookRoutingDtoToBook(BookCreationDto bookCreationDto);
}
