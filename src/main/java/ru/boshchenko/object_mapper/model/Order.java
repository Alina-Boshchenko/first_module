package ru.boshchenko.object_mapper.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {


    @Column(name = "order_date", nullable = false)
    @NotNull(message = "order_date is required")
    @PastOrPresent(message = "order_date must be in the past or present")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @Column(name = "shipping_address", nullable = false)
    @NotBlank(message = "shipping_address is required")
    private String shippingAddress;

    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    @NotNull(message = "total_price is required")
    @DecimalMin(value = "0.01", message = "amount must be at least 0.01")
    @Digits(integer = 10, fraction = 2, message = "invalid amount format")
    private BigDecimal totalPrice;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "order_status is required")
    private OrderStatus orderStatus;

    //TODO не сделала связь, посмотреть че как
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "products_orders",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Product> products = new ArrayList<>();


}
