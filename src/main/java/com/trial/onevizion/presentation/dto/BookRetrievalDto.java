package com.trial.onevizion.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRetrievalDto {
    private long id;
    private String title;
    private String author;
    private String description;
}
