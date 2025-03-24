package ru.boshchenko.projections.service.inter;

import ru.boshchenko.projections.dto.JwtResponse;

public interface AuthService {

    JwtResponse authenticate(String username, String password);
}
