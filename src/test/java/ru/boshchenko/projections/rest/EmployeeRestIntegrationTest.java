package ru.boshchenko.projections.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.boshchenko.projections.dto.EmployeeDto;
import ru.boshchenko.projections.service.EmployeeService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeRestIntegrationTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeRest employeeRest;

    @BeforeEach
    void setUp() {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
        SortHandlerMethodArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver();
        mockMvc = MockMvcBuilders.standaloneSetup(employeeRest)
                .setCustomArgumentResolvers(pageableResolver)
                .build();
    }

    @Test
    void getAll_ShouldReturnPagedDtos() throws Exception {
        EmployeeDto dto = createSampleDto();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "lastName"));
        Page<EmployeeDto> page = new PageImpl<>(List.of(dto), pageable, 1);

        when(employeeService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/employee/all")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "lastName,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId().toString()))
                .andExpect(jsonPath("$.content[0].first_name").value("John"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    void getOne_ShouldReturnEmployeeDto() throws Exception {
        EmployeeDto dto = createSampleDto();

        when(employeeService.getOne(dto.getId())).thenReturn(dto);

        mockMvc.perform(get("/api/employee/{id}", dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId().toString()))
                .andExpect(jsonPath("$.first_name").value("John"));
    }

    @Test
    void create_ShouldReturnCreatedDto() throws Exception {
        EmployeeDto dto = createSampleDto();

        when(employeeService.create(any(EmployeeDto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(dto.getId().toString()))
                .andExpect(jsonPath("$.position").value("Developer"));
    }

    private EmployeeDto createSampleDto() {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(UUID.randomUUID());
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPosition("Developer");
        dto.setSalary(BigDecimal.valueOf(5000));
        dto.setDepartmentId(UUID.randomUUID());
        return dto;
    }
}