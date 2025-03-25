package ru.boshchenko.oauth2.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.boshchenko.oauth2.model.User;
import ru.boshchenko.oauth2.service.inter.UserService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("Попытка аутентификации через {}", provider);
        OAuth2User userAuth = super.loadUser(userRequest);
        Map<String, Object> attributes = userAuth.getAttributes();

        String email = (String) attributes.get("email");
        if (email == null) {
            email = fetchEmailFromGitHub(userRequest.getAccessToken());
        }
        String role = determineRole(email);

        User user = userService.createOrUpdateUser(
                userAuth.getName(),
                provider,
                (String) attributes.get("login"),
                email,
                role
        );
        log.info("User roles before conversion: {}", user.getRoles());
        log.info("Успешная аутентификация пользователя: {}", user.getUsername());

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(el -> new SimpleGrantedAuthority("ROLE_"+el))
                .collect(Collectors.toSet());
        log.info("Final authorities: {}", authorities);
        return new DefaultOAuth2User(authorities, attributes,"id");
    }

    public String fetchEmailFromGitHub(OAuth2AccessToken token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.github.com/user/emails";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getTokenValue());
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, List.class
        );

        List<Map<String, Object>> emails = response.getBody();
        return (String) emails.get(0).get("email");
    }

    public String determineRole(String email) {
        return email.contains("admin") ? "ADMIN" : "USER";
    }










    public Set<GrantedAuthority> convertRoles(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
