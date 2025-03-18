package ru.boshchenko.pageable_library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

}
