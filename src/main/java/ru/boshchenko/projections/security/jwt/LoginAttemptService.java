package ru.boshchenko.projections.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.boshchenko.projections.model.User;
import ru.boshchenko.projections.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    private final UserRepository userRepository;
    private static final int MAX_ATTEMPTS = 5;

    public void loginFailed(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

        if (user.getFailedLoginAttempts() >= MAX_ATTEMPTS) {
            user.setAccountNonLocked(false);
        }

        userRepository.save(user);
    }

    public void loginSuccess(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFailedLoginAttempts(0);
        userRepository.save(user);
    }
}
