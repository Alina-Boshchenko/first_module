package ru.boshchenko.projections.service.inter;

import org.springframework.stereotype.Service;
import ru.boshchenko.projections.model.User;

@Service
public interface UserService {

    User findByUsername(String username);



}
