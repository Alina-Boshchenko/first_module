package ru.boshchenko.json_view.mapper;

import ru.boshchenko.json_view.dto.request.UserRequest;
import ru.boshchenko.json_view.dto.request.UserRequestPatch;
import ru.boshchenko.json_view.dto.response.UserResponse;
import ru.boshchenko.json_view.model.User;

public class UserMapper {

    private final OrderMapper orderMapper = new OrderMapper();

    public User toUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        return user;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());

        userResponse.setOrderResponses(user.getOrders().stream()
                .map(orderMapper::toOrderResponse)
                .toList());

        return userResponse;
    }

    public void updateUserFromRequest(UserRequestPatch userRequest, User user) {
        if (userRequest.getFirstName() != null) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            user.setLastName(userRequest.getLastName());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
    }

}
