package ru.boshchenko.json_view.service;

import org.springframework.stereotype.Service;
import ru.boshchenko.json_view.dto.request.UserRequest;
import ru.boshchenko.json_view.dto.request.UserRequestPatch;
import ru.boshchenko.json_view.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    UserResponse save(UserRequest userRequest);
    UserResponse findById(UUID id);
    void deleteById(UUID id);
    List<UserResponse> findAll();
    UserResponse update(UUID id, UserRequestPatch userRequest);

}
