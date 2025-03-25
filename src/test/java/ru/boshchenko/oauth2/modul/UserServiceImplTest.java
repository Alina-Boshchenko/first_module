package ru.boshchenko.oauth2.modul;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.boshchenko.oauth2.exception.UsernameCorrectnessException;
import ru.boshchenko.oauth2.model.User;
import ru.boshchenko.oauth2.projections.UserFull;
import ru.boshchenko.oauth2.projections.UserProfileDTO;
import ru.boshchenko.oauth2.repo.UserRepo;
import ru.boshchenko.oauth2.service.UserServiceImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private final Long TEST_USER_ID = 1L;
    private final String TEST_PROVIDER_ID = "12345";
    private final String TEST_PROVIDER = "github";
    private final String TEST_USERNAME = "testUser";
    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_ROLE = "USER";

    @Test
    void getUserFullParamShouldReturnUserFullWhenUserExists() {
        UserFull mockUserFull = new UserFull() {
            @Override public Long getId() { return TEST_USER_ID; }
            @Override public String getProviderId() { return TEST_PROVIDER_ID; }
            @Override public String getProvider() { return TEST_PROVIDER; }
            @Override public String getUsername() { return TEST_USERNAME; }
            @Override public String getEmail() { return TEST_EMAIL; }
        };
        when(userRepo.findProjectedByUsername(TEST_USERNAME, UserFull.class)).thenReturn(Optional.of(mockUserFull));
        UserFull result = userService.getUserFullParam(TEST_USERNAME);
        assertNotNull(result);
        assertEquals(TEST_USER_ID, result.getId());
        assertEquals(TEST_PROVIDER_ID, result.getProviderId());
        assertEquals(TEST_PROVIDER, result.getProvider());
        assertEquals(TEST_USERNAME, result.getUsername());
        assertEquals(TEST_EMAIL, result.getEmail());
        verify(userRepo).findProjectedByUsername(TEST_USERNAME, UserFull.class);
    }

    @Test
    void getUserProfileDTOShouldReturnProfileWhenUserExists() {
        UserProfileDTO mockProfile = new UserProfileDTO() {
            @Override public String getProvider() { return TEST_PROVIDER; }
            @Override public String getUsername() { return TEST_USERNAME; }
            @Override public String getEmail() { return TEST_EMAIL; }
        };
        when(userRepo.findProjectedByUsername(TEST_USERNAME, UserProfileDTO.class)).thenReturn(Optional.of(mockProfile));
        UserProfileDTO result = userService.getUserWithProviderAndLogin(TEST_USERNAME);
        assertNotNull(result);
        assertEquals(TEST_PROVIDER, result.getProvider());
        assertEquals(TEST_USERNAME, result.getUsername());
        assertEquals(TEST_EMAIL, result.getEmail());
        verify(userRepo).findProjectedByUsername(TEST_USERNAME, UserProfileDTO.class);
    }

    @Test
    void createOrUpdateUserShouldCreateNewUserWhenNotExists() {
        when(userRepo.findByProviderIdAndProvider(TEST_PROVIDER_ID, TEST_PROVIDER)).thenReturn(Optional.empty());
        User expectedUser = new User();
        expectedUser.setProviderId(TEST_PROVIDER_ID);
        expectedUser.setProvider(TEST_PROVIDER);
        expectedUser.setUsername(TEST_USERNAME);
        expectedUser.setEmail(TEST_EMAIL);
        expectedUser.setRoles(Set.of(TEST_ROLE));
        when(userRepo.save(any(User.class))).thenReturn(expectedUser);
        User result = userService.createOrUpdateUser(
                TEST_PROVIDER_ID,
                TEST_PROVIDER,
                TEST_USERNAME,
                TEST_EMAIL,
                TEST_ROLE);
        assertNotNull(result);
        assertEquals(TEST_PROVIDER_ID, result.getProviderId());
        assertEquals(TEST_PROVIDER, result.getProvider());
        assertEquals(TEST_USERNAME, result.getUsername());
        assertEquals(TEST_EMAIL, result.getEmail());
        assertTrue(result.getRoles().contains(TEST_ROLE));
        verify(userRepo).findByProviderIdAndProvider(TEST_PROVIDER_ID, TEST_PROVIDER);
        verify(userRepo).save(any(User.class));
    }

    @Test
    void createOrUpdateUserShouldUpdateUserWhenExists() {
        User existingUser = new User();
        existingUser.setId(TEST_USER_ID);
        existingUser.setProviderId(TEST_PROVIDER_ID);
        existingUser.setProvider(TEST_PROVIDER);
        existingUser.setUsername("oldUsername");
        existingUser.setEmail("old@example.com");
        existingUser.setRoles(new HashSet<>(Set.of("OLD_ROLE")));
        when(userRepo.findByProviderIdAndProvider(TEST_PROVIDER_ID, TEST_PROVIDER)).thenReturn(Optional.of(existingUser));
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User result = userService.createOrUpdateUser(
                TEST_PROVIDER_ID,
                TEST_PROVIDER,
                TEST_USERNAME,
                TEST_EMAIL,
                TEST_ROLE);
        assertNotNull(result);
        assertEquals(TEST_USER_ID, result.getId());
        assertEquals(TEST_USERNAME, result.getUsername());
        assertEquals(TEST_EMAIL, result.getEmail());
        assertTrue(result.getRoles().contains("OLD_ROLE"));
        assertTrue(result.getRoles().contains(TEST_ROLE));
        verify(userRepo).findByProviderIdAndProvider(TEST_PROVIDER_ID, TEST_PROVIDER);
        verify(userRepo).save(existingUser);
    }

    @Test
    void getUserFullParamShouldThrowExceptionWhenUserNotFound() {
        when(userRepo.findProjectedByUsername("unknown", UserFull.class)).thenReturn(Optional.empty());
        assertThrows(UsernameCorrectnessException.class, () -> {
            userService.getUserFullParam("unknown");});
        verify(userRepo).findProjectedByUsername("unknown", UserFull.class);
    }

    @Test
    void allUserFullParamShouldReturnCollection() {
        UserFull mockUser = new UserFull() {
            @Override public Long getId() { return TEST_USER_ID; }
            @Override public String getProviderId() { return TEST_PROVIDER_ID; }
            @Override public String getProvider() { return TEST_PROVIDER; }
            @Override public String getUsername() { return TEST_USERNAME; }
            @Override public String getEmail() { return TEST_EMAIL; }
        };
        when(userRepo.findAllBy(UserFull.class)).thenReturn(Collections.singletonList(mockUser));
        var result = userService.allUserFullParam();
        assertEquals(1, result.size());
        assertEquals(TEST_USER_ID, result.iterator().next().getId());
        verify(userRepo).findAllBy(UserFull.class);
    }
}