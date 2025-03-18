package ru.boshchenko.pageable_library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.boshchenko.pageable_library.dto.request.AuthorRequest;
import ru.boshchenko.pageable_library.dto.response.AuthorResponse;
import ru.boshchenko.pageable_library.exception.ResourceNotFoundException;
import ru.boshchenko.pageable_library.mapper.AuthorMapper;
import ru.boshchenko.pageable_library.model.Author;
import ru.boshchenko.pageable_library.repo.AuthorRepository;
import ru.boshchenko.pageable_library.service.AuthorService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper authorMapper = new AuthorMapper();

    @Override
    public AuthorResponse create(AuthorRequest authorRequest) {
        Author author = authorMapper.toAuthor(authorRequest);
        repository.save(author);
        return authorMapper.toResponse(author);
    }

    @Override
    public AuthorResponse findById(UUID id) {
        Author author = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        return authorMapper.toResponse(author);
    }
}
