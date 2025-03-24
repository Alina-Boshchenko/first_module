package ru.boshchenko.oauth2.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityEventsListener {

    @EventListener
    public void handleAuthSuccess(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        log.info("Успешная аутентификация: {}", auth.getName());
    }

    @EventListener
    public void handleLogout(LogoutSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        log.info("Пользователь вышел: {}", auth.getName());
    }
}
