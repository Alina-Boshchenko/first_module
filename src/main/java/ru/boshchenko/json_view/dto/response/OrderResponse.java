package ru.boshchenko.json_view.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.boshchenko.json_view.model.OrderStatus;
import ru.boshchenko.json_view.views.Views;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderResponse {

    @JsonView(Views.UserDetails.class)
    private UUID id;

    @JsonView(Views.UserDetails.class)
    private BigDecimal amount;

    @JsonView(Views.UserDetails.class)
    private OrderStatus orderStatus;

}
