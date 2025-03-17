package ru.boshchenko.json_view.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.boshchenko.json_view.dto.request.UserRequest;
import ru.boshchenko.json_view.dto.request.UserRequestPatch;
import ru.boshchenko.json_view.dto.response.UserResponse;
import ru.boshchenko.json_view.exception.ResourceNotFoundException;
import ru.boshchenko.json_view.mapper.UserMapper;
import ru.boshchenko.json_view.model.User;
import ru.boshchenko.json_view.repository.UserRepository;
import ru.boshchenko.json_view.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();


    @Override
    public UserResponse save(UserRequest userRequest) {
        User user = userRepository.save(userMapper.toUser(userRequest));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> findAll() {
       return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse update(UUID id, UserRequestPatch userRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        userMapper.updateUserFromRequest(userRequest, user);
        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponse(updatedUser);
    }
}
