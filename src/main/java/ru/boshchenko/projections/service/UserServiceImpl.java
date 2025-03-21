package ru.boshchenko.projections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.boshchenko.projections.exception.ResourceNotFoundException;
import ru.boshchenko.projections.model.User;
import ru.boshchenko.projections.repo.UserRepository;
import ru.boshchenko.projections.service.inter.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
