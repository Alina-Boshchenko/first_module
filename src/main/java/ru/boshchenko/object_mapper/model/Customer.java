package ru.boshchenko.object_mapper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "first_name is required")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "invalid email format")
    @NotBlank(message = "email is required")
    private String email;

    @Column(name = "contact_number", nullable = false, unique = true)
    @Pattern(regexp = "^(\\+7|8)?[9]\\d{9}$", message = "Incorrect contact_number format")
    @NotBlank(message = "contact_number is required")
    private String contactNumber;

}
