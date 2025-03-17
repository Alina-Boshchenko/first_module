package ru.boshchenko.json_view.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.boshchenko.json_view.controller.UserRestController;
import ru.boshchenko.json_view.dto.request.UserRequest;
import ru.boshchenko.json_view.dto.request.UserRequestPatch;
import ru.boshchenko.json_view.dto.response.OrderResponse;
import ru.boshchenko.json_view.dto.response.UserResponse;
import ru.boshchenko.json_view.mapper.OrderMapper;
import ru.boshchenko.json_view.mapper.UserMapper;
import ru.boshchenko.json_view.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@Import({UserMapper.class, OrderMapper.class})
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID userId = UUID.randomUUID();
    private final UserResponse userResponse = new UserResponse();

    @BeforeEach
    void setUp() {
        userResponse.setId(userId);
        userResponse.setFirstName("Anna");
        userResponse.setLastName("Milk");
        userResponse.setEmail("anna@gmail.com");
    }

    @Test
    @DisplayName("GET /all возвращает UserSummary представление")
    void getAllUsers_ShouldReturnUserSummaryView() throws Exception {
        when(userService.findAll()).thenReturn(List.of(userResponse));

        mockMvc.perform(get("/api/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userId.toString()))
                .andExpect(jsonPath("$[0].orderResponses").doesNotExist());
    }

    @Test
    @DisplayName("GET /{id} возвращает UserDetails представление")
    void getUserById_ShouldReturnUserDetailsView() throws Exception {
        OrderResponse order = new OrderResponse();
        order.setId(UUID.randomUUID());
        order.setAmount(BigDecimal.TEN);
        userResponse.setOrderResponses(List.of(order));

        when(userService.findById(userId)).thenReturn(userResponse);

        mockMvc.perform(get("/api/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order_responses[0].id").exists());
    }

    @Test
    @DisplayName("POST /create валидирует входные данные")
    void createUser_ShouldValidateInput() throws Exception {
        UserRequest invalidRequest = new UserRequest();
        invalidRequest.setFirstName("");
        invalidRequest.setLastName("");
        invalidRequest.setEmail("invalid-email");

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("firstName is required"))
                .andExpect(jsonPath("$.lastName").value("lastName is required"))
                .andExpect(jsonPath("$.email").value("invalid email format"));
    }

    @Test
    @DisplayName("PATCH /{id} обновляет пользователя")
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        UserRequestPatch request = new UserRequestPatch();
        request.setFirstName("Vika");

        UserResponse updateResponse = new UserResponse();
        updateResponse.setId(userId);
        updateResponse.setFirstName("Vika");
        updateResponse.setLastName("Milk");
        updateResponse.setEmail("anna@gmail.com");

        when(userService.update(eq(userId), any(UserRequestPatch.class))).thenReturn(updateResponse);

        mockMvc.perform(patch("/api/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Vika"));
    }
}