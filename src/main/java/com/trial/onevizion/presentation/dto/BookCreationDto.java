package com.trial.onevizion.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookCreationRoutingDto {
    @NotBlank(message = "Title must not be blank")
    @Size(max = 150, message = "Title must not exceed 150 characters")
    private String title;

    @NotBlank(message = "Author must not be blank")
    @Size(max = 150, message = "Author must not exceed 150 characters")
    private String author;

    @Size(max = 150, message = "Description must not exceed 150 characters")
    private String description;
}
