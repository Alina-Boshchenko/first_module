package ru.boshchenko.pageable_library.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.boshchenko.pageable_library.model.Author;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
