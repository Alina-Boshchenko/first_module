package ru.boshchenko.json_view.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.boshchenko.json_view.views.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {

    @JsonView(Views.UserSummary.class)
    private UUID id;

    @JsonView(Views.UserSummary.class)
    private String firstName;

    @JsonView(Views.UserSummary.class)
    private String lastName;

    @JsonView(Views.UserSummary.class)
    private String email;

    @JsonView(Views.UserDetails.class)
    private List<OrderResponse> orderResponses = new ArrayList<>();

}
