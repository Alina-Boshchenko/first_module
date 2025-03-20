package ru.boshchenko.projections.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.boshchenko.projections.dto.DepartmentDto;
import ru.boshchenko.projections.exception.ResourceNotFoundException;
import ru.boshchenko.projections.mapper.DepartmentMapper;
import ru.boshchenko.projections.model.Department;
import ru.boshchenko.projections.repo.DepartmentRepository;
import ru.boshchenko.projections.service.DepartmentService;

import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    private final UUID testId = UUID.randomUUID();
    private Department testDepartment;
    private DepartmentDto testDepartmentDto;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setId(testId);
        testDepartment.setName("Test Department");

        testDepartmentDto = new DepartmentDto();
        testDepartmentDto.setId(testId);
        testDepartmentDto.setName("Test Department");
    }

    @Test
    void getOne() {
        when(departmentRepository.findById(testId)).thenReturn(Optional.of(testDepartment));
        when(departmentMapper.toDepartmentDto(testDepartment)).thenReturn(testDepartmentDto);

        DepartmentDto result = departmentService.getOne(testId);

        assertNotNull(result);
        assertEquals(testId, result.getId());
        verify(departmentRepository).findById(testId);
    }

    @Test
    void getOneNotFound() {
        when(departmentRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.getOne(testId));
    }

    @Test
    void create() {
        when(departmentMapper.toEntity(testDepartmentDto)).thenReturn(testDepartment);
        when(departmentRepository.save(testDepartment)).thenReturn(testDepartment);
        when(departmentMapper.toDepartmentDto(testDepartment)).thenReturn(testDepartmentDto);

        DepartmentDto result = departmentService.create(testDepartmentDto);

        assertNotNull(result);
        assertEquals(testId, result.getId());
        verify(departmentRepository).save(testDepartment);
    }

    @Test
    void delete() {
        when(departmentRepository.findById(testId)).thenReturn(Optional.of(testDepartment));
        when(departmentMapper.toDepartmentDto(testDepartment)).thenReturn(testDepartmentDto);

        DepartmentDto result = departmentService.delete(testId);

        assertNotNull(result);
        assertEquals(testId, result.getId());
        verify(departmentRepository).delete(testDepartment);
    }
}
