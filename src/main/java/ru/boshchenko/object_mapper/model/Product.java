package ru.boshchenko.object_mapper.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "name is required")
    private String name;

    @Column(name = "description", length = 510)
    private String description;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    @NotNull(message = "price is required")
    @DecimalMin(value = "0.01", message = "amount must be at least 0.01")
    @Digits(integer = 10, fraction = 2, message = "invalid amount format")
    private BigDecimal price;

    @Column(name = "quantity_in_stock", nullable = false)
    @NotNull(message = "quantity in stock is required")
    @PositiveOrZero(message = "quantity in stock cannot be negative")
    private Integer quantityInStock;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders = new ArrayList<>();


}
