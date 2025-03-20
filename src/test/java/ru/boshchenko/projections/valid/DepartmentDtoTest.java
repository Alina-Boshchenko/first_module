package ru.boshchenko.projections.valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import ru.boshchenko.projections.dto.DepartmentDto;

import java.util.Set;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class DepartmentDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @Test
    void whenCreatingDepartmentDto_ThenSettersAndGettersWorkCorrectly() {
        UUID testId = UUID.randomUUID();
        DepartmentDto dto = new DepartmentDto();
        dto.setId(testId);
        dto.setName("Engineering");

        assertThat(dto.getId()).isEqualTo(testId);
        assertThat(dto.getName()).isEqualTo("Engineering");
    }

    @Test
    void whenNameIsBlank_ThenValidationFails() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("   ");

        Set<ConstraintViolation<DepartmentDto>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("name is required");
    }

    @Test
    void whenNameIsValid_ThenValidationSucceeds() {
        DepartmentDto dto = new DepartmentDto();
        dto.setName("Valid Department");

        Set<ConstraintViolation<DepartmentDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenSerializingToJson_thenCorrectSnakeCase() throws JsonProcessingException {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        dto.setName("HR Department");

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"id\":\"550e8400-e29b-41d4-a716-446655440000\"");
        assertThat(json).contains("\"name\":\"HR Department\"");
    }

    @Test
    void whenDeserializingFromJson_thenIgnoresUnknownProperties() throws JsonProcessingException {
        String json = """
            {
                "id": "550e8400-e29b-41d4-a716-446655440000",
                "name": "Finance",
                "unknown_property": "value",
                "another_unknown": 123
            }
            """;

        DepartmentDto dto = objectMapper.readValue(json, DepartmentDto.class);

        assertThat(dto.getId()).isEqualTo(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        assertThat(dto.getName()).isEqualTo("Finance");
    }
}
