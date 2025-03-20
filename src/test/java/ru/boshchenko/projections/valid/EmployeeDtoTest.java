package ru.boshchenko.projections.valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.boshchenko.projections.dto.EmployeeDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class EmployeeDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    private EmployeeDto createValidDto() {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(UUID.randomUUID());
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPosition("Developer");
        dto.setSalary(new BigDecimal("5000.00"));
        dto.setDepartmentId(UUID.randomUUID());
        return dto;
    }

    @Test
    void whenAllFieldsValid_ThenNoViolations() {
        EmployeeDto dto = createValidDto();
        Set<ConstraintViolation<EmployeeDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenRequiredFieldsBlank_ThenValidationFails() {
        EmployeeDto dto = createValidDto();
        dto.setFirstName(" ");
        dto.setLastName(" ");
        dto.setPosition(" ");
        dto.setSalary(null);
        dto.setDepartmentId(null);

        Set<ConstraintViolation<EmployeeDto>> violations = validator.validate(dto);

        assertThat(violations).hasSize(5)
                .extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "first_name is required",
                        "last_name is required",
                        "position is required",
                        "salary is required",
                        "department_id is required"
                );
    }

    @Test
    void whenSalaryInvalid_ThenValidationFails() {
        EmployeeDto dto = createValidDto();

        // Negative salary
        dto.setSalary(new BigDecimal("-100"));
        assertValidationMessage(dto, "invalid salary format");

        // Too many decimal places
        dto.setSalary(new BigDecimal("5000.123"));
        assertValidationMessage(dto, "invalid salary format");

        // Too many integer digits
        dto.setSalary(new BigDecimal("123456789012.00"));
        assertValidationMessage(dto, "invalid salary format");
    }

    @Test
    void whenSerializingToJson_thenCorrectSnakeCase() throws JsonProcessingException {
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        UUID departmentId = UUID.fromString("67e55044-10b1-426f-9247-bb680a5fe478");

        EmployeeDto dto = createValidDto();
        dto.setId(id);
        dto.setDepartmentId(departmentId);

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains(
                "\"first_name\":\"John\"",
                "\"last_name\":\"Doe\"",
                "\"position\":\"Developer\"",
                "\"salary\":5000.00",
                "\"department_id\":\"" + departmentId + "\""
        );
    }

    @Test
    void whenDeserializingFromJson_thenCorrectMapping() throws JsonProcessingException {
        String json = """
            {
                "id": "550e8400-e29b-41d4-a716-446655440000",
                "first_name": "Alice",
                "last_name": "Smith",
                "position": "Manager",
                "salary": 75000.50,
                "department_id": "67e55044-10b1-426f-9247-bb680a5fe478",
                "extra_field": "ignored"
            }
            """;

        EmployeeDto dto = objectMapper.readValue(json, EmployeeDto.class);

        assertThat(dto.getFirstName()).isEqualTo("Alice");
        assertThat(dto.getLastName()).isEqualTo("Smith");
        assertThat(dto.getPosition()).isEqualTo("Manager");
        assertThat(dto.getSalary()).isEqualTo(new BigDecimal("75000.50"));
        assertThat(dto.getDepartmentId()).isEqualTo(UUID.fromString("67e55044-10b1-426f-9247-bb680a5fe478"));
    }

    private void assertValidationMessage(EmployeeDto dto, String expectedMessage) {
        Set<ConstraintViolation<EmployeeDto>> violations = validator.validate(dto);
        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals(expectedMessage));
    }

    @Test
    void whenSalaryZero_ThenValidationSuccess() {
        EmployeeDto dto = createValidDto();
        dto.setSalary(BigDecimal.ZERO);
        assertThat(validator.validate(dto)).isEmpty();
    }

    @Test
    void whenSalaryMaxValue_ThenValidationSuccess() {
        EmployeeDto dto = createValidDto();
        dto.setSalary(new BigDecimal("9999999999.99"));
        assertThat(validator.validate(dto)).isEmpty();
    }
}
