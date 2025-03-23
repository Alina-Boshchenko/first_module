package ru.boshchenko.projections.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            String authHeader = request.getHeader("Authorization");
            String user = authHeader != null && authHeader.startsWith("Bearer ") ?
                    jwtUtils.extractUsername(authHeader.substring(7)) : "anonymous";

            logger.info(
                    "Request: {} {} | IP: {} | User: {} | Status: {} | Duration: {}ms",
                    request.getMethod(),
                    request.getRequestURI(),
                    request.getRemoteAddr(),
                    user,
                    response.getStatus(),
                    duration
            );
        }
    }
}
