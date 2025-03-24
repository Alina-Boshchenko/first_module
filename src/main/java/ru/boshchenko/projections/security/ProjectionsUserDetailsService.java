package ru.boshchenko.projections.security;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.boshchenko.projections.exception.ResourceNotFoundException;
import ru.boshchenko.projections.model.User;
import ru.boshchenko.projections.service.inter.UserService;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class ProjectionsUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        User user = userService.findByUsername(username);
        return new ProjectionsUserDetails(user);
    }

}
