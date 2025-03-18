package ru.boshchenko.pageable_library.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "title is required")
    private String title;

    @PastOrPresent(message = "publication date must be in the past or present")
    @NotNull(message = "publication date is required")
    private LocalDate publicationDate;

    @NotNull(message = "author_id is required")
    private UUID authorId;
}
