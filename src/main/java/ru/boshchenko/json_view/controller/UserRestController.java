package ru.boshchenko.json_view.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.json_view.dto.request.UserRequest;
import ru.boshchenko.json_view.dto.request.UserRequestPatch;
import ru.boshchenko.json_view.dto.response.UserResponse;
import ru.boshchenko.json_view.service.UserService;
import ru.boshchenko.json_view.views.Views;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/all")
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<List<UserResponse>> getListAllUsersNoOrderDetails() {
        List<UserResponse> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<UserResponse> getUsersOrderDetails(@PathVariable UUID id) {
        UserResponse userResponse = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/create")
    @JsonView(Views.UserSummary.class)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.save(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PatchMapping("/{id}")
    @JsonView(Views.UserDetails.class)
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @Valid @RequestBody UserRequestPatch userRequest){
        UserResponse userResponse = userService.update(id,userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
