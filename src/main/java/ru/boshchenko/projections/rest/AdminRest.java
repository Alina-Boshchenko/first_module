package ru.boshchenko.projections.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.boshchenko.projections.dto.UnlockRequest;
import ru.boshchenko.projections.service.inter.UserService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRest {

    private final UserService userService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/unlock-account")
    public ResponseEntity<?> unlockUserAccount(@Valid @RequestBody UnlockRequest request) {
        userService.unlockUserAccount(request.getUsername());
        return ResponseEntity.ok("Account unlocked successfully");
    }
}
