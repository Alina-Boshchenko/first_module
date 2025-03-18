package ru.boshchenko.pageable_library.dto.request;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestPatch {

    private String title;

    @PastOrPresent(message = "publication date must be in the past or present")
    private LocalDate publicationDate;

}
