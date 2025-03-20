package ru.boshchenko.projections.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.boshchenko.projections.dto.DepartmentDto;
import ru.boshchenko.projections.exception.ResourceNotFoundException;
import ru.boshchenko.projections.mapper.DepartmentMapper;
import ru.boshchenko.projections.model.Department;
import ru.boshchenko.projections.repo.DepartmentRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    private final ObjectMapper objectMapper;

    public DepartmentDto getOne(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return departmentMapper.toDepartmentDto(department);
    }

    public DepartmentDto create(DepartmentDto dto) {
        Department department = departmentMapper.toEntity(dto);
        Department resultDepartment = departmentRepository.save(department);
        return departmentMapper.toDepartmentDto(resultDepartment);
    }

    @SneakyThrows
    public DepartmentDto patch(UUID id, JsonNode patchNode) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        DepartmentDto departmentDto = departmentMapper.toDepartmentDto(department);
        objectMapper.readerForUpdating(departmentDto).readValue(patchNode);
        departmentMapper.updateWithNull(departmentDto, department);

        Department resultDepartment = departmentRepository.save(department);
        return departmentMapper.toDepartmentDto(resultDepartment);
    }

    public DepartmentDto delete(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        departmentRepository.delete(department);
        return departmentMapper.toDepartmentDto(department);
    }

}
