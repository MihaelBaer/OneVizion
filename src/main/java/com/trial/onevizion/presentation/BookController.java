package com.trial.onevizion.presentation;

import com.trial.onevizion.application.abstraction.BookService;
import com.trial.onevizion.presentation.dto.BookCreationDto;
import com.trial.onevizion.presentation.dto.BookRetrievalDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
@Tag(name = "Books API", description = "All books related operations")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400",
                     description = "Invalid format or missing required parameters",
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500",
                     description = "Unexpected server error",
                     content = @Content(mediaType = "application/json"))
})
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Retrieve all books sorted by title in descending order",
               description = "Returns a list of books sorted by title in descending order")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of books",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookCreationDto.class)))
    @GetMapping(value = "/desc", produces = "application/json")
    public ResponseEntity<List<BookRetrievalDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooksSortedDescByTitle());
    }

    @Operation(summary = "Retrieve books grouped by author",
               description = "Returns a map where each key is an author's name and the value is the list of their books")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved books grouped by author",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookCreationDto.class)))
    @GetMapping(value = "/by-author", produces = "application/json")
    public ResponseEntity<Map<String, List<BookRetrievalDto>>> getBooksGroupedByAuthor() {
        return ResponseEntity.ok(bookService.findBooksGroupedByAuthor());
    }

    @Operation(summary = "Get top authors by the occurrences of a specified character in book titles",
               description = "Returns a list of authors and the count of the specified character in the titles of their books")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved top authors by character occurrences",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.Entry.class)))
    @GetMapping(value = "/top-char-occurrences", produces = "application/json")
    public ResponseEntity<List<Map.Entry<String, Integer>>> getTopAuthorsByCharOccurrence(
            @RequestParam("char") String character) {
        return ResponseEntity.ok(bookService.findTopAuthorsByCharacterOccurrence(character));
    }

    @Operation(summary = "Add a new book",
               description = "Creates a new book and returns the created book data")
    @ApiResponse(responseCode = "201", description = "Book successfully created",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookCreationDto.class)))
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<BookCreationDto> addBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "JSON request of the new book to be created", required = true,
                                                                  content = @Content(schema = @Schema(implementation = BookCreationDto.class)))
            @Valid @RequestBody BookCreationDto newBook) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(newBook));
    }
}