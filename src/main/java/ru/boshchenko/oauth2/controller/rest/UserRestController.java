package ru.boshchenko.oauth2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.oauth2.projections.UserProfileDTO;
import ru.boshchenko.oauth2.service.inter.UserService;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getUserSettings(@AuthenticationPrincipal OAuth2User principal) {
        String username = principal.getAttribute("login");
        UserProfileDTO user = userService.getUserWithProviderAndLogin(username);
        return ResponseEntity.ok(user);
    }

}
