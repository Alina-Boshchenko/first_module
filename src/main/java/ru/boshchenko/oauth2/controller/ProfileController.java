package ru.boshchenko.oauth2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.boshchenko.oauth2.service.inter.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal OAuth2User user){
        String username = user.getAttribute("login");
        String email = userService.getUserEmail(username).getEmail();
        Map<String, Object> profileData = new HashMap<>();
        profileData.put("name", user.getName());
        profileData.put("email", email);
        profileData.put("login", username);
        model.addAllAttributes(profileData);
        return "profile";
    }
}
