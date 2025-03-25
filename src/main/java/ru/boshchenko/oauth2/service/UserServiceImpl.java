package ru.boshchenko.oauth2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.boshchenko.oauth2.exception.UsernameCorrectnessException;
import ru.boshchenko.oauth2.model.User;
import ru.boshchenko.oauth2.projections.UserEmail;
import ru.boshchenko.oauth2.projections.UserFull;
import ru.boshchenko.oauth2.projections.UserProfileDTO;
import ru.boshchenko.oauth2.repo.UserRepo;
import ru.boshchenko.oauth2.service.inter.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public UserFull getUserFullParam(String username){
        return userRepo.findProjectedByUsername(username, UserFull.class)
                .orElseThrow(() -> new UsernameCorrectnessException("username does not exist"));
    }

    @Override
    public UserEmail getUserEmail(String username){
        return userRepo.findProjectedByUsername(username,UserEmail.class)
                .orElseThrow(() -> new UsernameCorrectnessException("username does not exist"));
    }

    @Override
    public UserProfileDTO getUserWithProviderAndLogin(String username){
        return userRepo.findProjectedByUsername(username, UserProfileDTO.class)
                .orElseThrow(() -> new UsernameCorrectnessException("username does not exist"));
    }

    @Override
    public Collection<UserFull> allUserFullParam(){
        return userRepo.findAllBy(UserFull.class);
    }

    @Override
    public User createOrUpdateUser(String providerId,
                                   String provider,
                                   String username,
                                   String email,
                                   String role) {
        return userRepo.findByProviderIdAndProvider(providerId, provider)
                .map(existingUser -> updateUser(existingUser, username, email, role))
                .orElseGet(() -> createUser(providerId, provider, username, email, role));
    }

    @Override
    public void deleteById(Long id){
        userRepo.deleteById(id);
    }

    private User createUser(String providerId,
                            String provider,
                            String username,
                            String email,
                            String role) {
        User user = new User();
        user.setProviderId(providerId);
        user.setProvider(provider);
        user.setUsername(username);
        user.setEmail(email);
        user.setRoles(new HashSet<>(Set.of(Objects.requireNonNull(role))));
        return userRepo.save(user);
    }

    private User updateUser(User user,
                            String username,
                            String email,
                            String role) {
        if (username != null) user.setUsername(username);
        if (email != null) user.setEmail(email);
        user.getRoles().add(Objects.requireNonNull(role));
        return userRepo.save(user);
    }

}
