package ru.boshchenko.projections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.boshchenko.projections.dto.JwtResponse;
import ru.boshchenko.projections.exception.AuthException;
import ru.boshchenko.projections.model.User;
import ru.boshchenko.projections.security.ProjectionsUserDetailsService;
import ru.boshchenko.projections.security.jwt.JwtUtils;
import ru.boshchenko.projections.security.jwt.LoginAttemptService;
import ru.boshchenko.projections.service.inter.AuthService;
import ru.boshchenko.projections.service.inter.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final LoginAttemptService loginAttemptService;
    private final UserService userService;

    public JwtResponse authenticate(String username, String password) {
        User user = userService.findByUsername(username);
        if (!user.isAccountNonLocked()) {
            throw new AuthException("User account is locked");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            loginAttemptService.loginSuccess(username);
            return new JwtResponse(jwtUtils.generateToken(userDetails));

        } catch (BadCredentialsException e) {
            loginAttemptService.loginFailed(username);
            throw new AuthException("Invalid credentials", e);
        }
    }
}
