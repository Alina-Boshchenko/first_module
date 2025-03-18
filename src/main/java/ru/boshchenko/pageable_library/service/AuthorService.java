package ru.boshchenko.pageable_library.service;

import org.springframework.stereotype.Service;
import ru.boshchenko.pageable_library.dto.request.AuthorRequest;
import ru.boshchenko.pageable_library.dto.response.AuthorResponse;

import java.util.UUID;

@Service
public interface AuthorService {

    AuthorResponse create(AuthorRequest authorRequest);

    AuthorResponse findById(UUID id);
}
