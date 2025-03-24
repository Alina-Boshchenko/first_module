package ru.boshchenko.oauth2.service.inter;

import org.springframework.stereotype.Service;
import ru.boshchenko.oauth2.model.User;
import ru.boshchenko.oauth2.projections.UserEmail;
import ru.boshchenko.oauth2.projections.UserFull;
import ru.boshchenko.oauth2.projections.UserProfileDTO;

import java.util.Collection;

@Service
public interface UserService {

    User createOrUpdateUser(String providerId,
                            String provider,
                            String username,
                            String email,
                            String role);

    UserFull getUserFullParam(String username);
    UserProfileDTO getUserWithProviderAndLogin(String username);
    Collection<UserFull> allUserFullParam();
    void deleteById(Long id);
    UserEmail getUserEmail(String username);

}
