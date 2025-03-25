package ru.boshchenko.oauth2.modul;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import ru.boshchenko.oauth2.model.User;
import ru.boshchenko.oauth2.service.CustomOAuth2UserService;
import ru.boshchenko.oauth2.service.inter.UserService;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceTest {

    @Mock
    private UserService userService;

    private CustomOAuth2UserService customOAuth2UserService;

    private Map<String, Object> userAttributes;
    private User testUser;

    @BeforeEach
    void setUp() {
        customOAuth2UserService = new CustomOAuth2UserService(userService);
        userAttributes = new HashMap<>();
        userAttributes.put("id", "12345");
        userAttributes.put("login", "testUser");
        userAttributes.put("name", "Test User");
        userAttributes.put("email", "test@example.com");
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setRoles(Set.of("USER"));
    }

    @Test
    void minimalTest() {
        UserService userServiceMock = mock(UserService.class);
        CustomOAuth2UserService service = new CustomOAuth2UserService(userServiceMock);
        User user = new User();
        user.setRoles(Set.of("USER"));
        Set<GrantedAuthority> authorities = service.convertRoles(user);
        assertThat(authorities)
                .isNotEmpty()
                .extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_USER");
    }

    @Test
    void testRoleConversionDirectly() {
        User user = new User();
        user.setRoles(Set.of("USER"));
        CustomOAuth2UserService service = new CustomOAuth2UserService(mock(UserService.class));
        Set<GrantedAuthority> authorities = service.convertRoles(user);
        assertThat(authorities)
                .extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_USER");
    }

    @Test
    void determineRoleShouldReturnAdminForAdminEmail() {
        String adminRole = customOAuth2UserService.determineRole("admin@company.com");
        String userRole = customOAuth2UserService.determineRole("user@example.com");
        assertEquals("ADMIN", adminRole);
        assertEquals("USER", userRole);
    }

}