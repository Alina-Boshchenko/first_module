package ru.boshchenko.json_view.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.boshchenko.json_view.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.01", message = "amount must be at least 0.01")
    @Digits(integer = 10, fraction = 2, message = "invalid amount format")
    private BigDecimal amount;

    @NotNull(message = "orderStatus is required")
    private OrderStatus orderStatus;

    @NotEmpty(message = "productsId is empty")
    private List<@NotNull UUID> productsId;

    @NotNull(message = "userId is required")
    private UUID userId;

}
