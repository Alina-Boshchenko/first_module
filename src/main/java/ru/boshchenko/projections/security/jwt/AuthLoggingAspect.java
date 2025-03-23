package ru.boshchenko.projections.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.boshchenko.projections.dto.JwtResponse;

@Aspect
@Component
@Slf4j
public class AuthLoggingAspect {

    @AfterReturning(pointcut = "execution(* ru.boshchenko.projections.rest.AuthRest.authenticateUser(..))", returning = "response")
    public void logSuccessLogin(JwtResponse response) {
        log.info("Successful login. Token generated: {}", response.getToken());
    }

    @AfterThrowing(pointcut = "execution(* ru.boshchenko.projections.rest.AuthRest.authenticateUser(..))", throwing = "ex")
    public void logFailedLogin(Exception ex) {
        log.error("Login attempt failed: {}", ex.getMessage());
    }

}
