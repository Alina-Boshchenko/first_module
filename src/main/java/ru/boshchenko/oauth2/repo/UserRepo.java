package ru.boshchenko.oauth2.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.boshchenko.oauth2.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByProviderIdAndProvider(String providerId, String provider);

    <T> Optional<T> findProjectedByUsername(String username, Class<T> type);

    <T> Collection<T> findAllBy(Class<T> type);
}
