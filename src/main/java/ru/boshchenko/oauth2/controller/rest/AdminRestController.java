package ru.boshchenko.oauth2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.oauth2.projections.UserFull;
import ru.boshchenko.oauth2.service.inter.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminRestController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Collection<UserFull>> getAllUsers() {
        return ResponseEntity.ok(userService.allUserFullParam());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
