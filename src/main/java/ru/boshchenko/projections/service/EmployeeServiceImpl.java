package ru.boshchenko.projections.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.boshchenko.projections.dto.EmployeeDto;
import ru.boshchenko.projections.exception.ResourceNotFoundException;
import ru.boshchenko.projections.mapper.EmployeeMapper;
import ru.boshchenko.projections.model.Department;
import ru.boshchenko.projections.model.Employee;
import ru.boshchenko.projections.repo.DepartmentRepository;
import ru.boshchenko.projections.repo.EmployeeRepository;
import ru.boshchenko.projections.service.inter.EmployeeService;
import ru.boshchenko.projections.spel.EmployeeProjection;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final EmployeeMapper employeeMapper;

    private final ObjectMapper objectMapper;

    public EmployeeProjection findProjectionById(UUID id) {
        return employeeRepository.findProjectionById(id);
    }

    public List<EmployeeProjection> findAllProjectionsBy() {
        return employeeRepository.findAllProjectionsBy();
    }

    public Page<EmployeeDto> getAll(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.map(employeeMapper::toEmployeeDto);
    }

    public EmployeeDto getOne(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return employeeMapper.toEmployeeDto(employee);
    }

    public List<EmployeeDto> getMany(List<UUID> ids) {
        List<Employee> employees = employeeRepository.findAllById(ids);
        return employees.stream()
                .map(employeeMapper::toEmployeeDto)
                .toList();
    }

    public EmployeeDto create(EmployeeDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        Employee employee = employeeMapper.toEntity(dto);
        employee.setDepartment(department);

        Employee resultEmployee = employeeRepository.save(employee);

        return employeeMapper.toEmployeeDto(resultEmployee);
    }

    @SneakyThrows
    public EmployeeDto patch(UUID id, JsonNode patchNode) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        EmployeeDto employeeDto = employeeMapper.toEmployeeDto(employee);
        objectMapper.readerForUpdating(employeeDto).readValue(patchNode);
        employeeMapper.updateWithNull(employeeDto, employee);

        Employee resultEmployee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(resultEmployee);
    }

    @SneakyThrows
    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) {
        List<Employee> employees = employeeRepository.findAllById(ids);

        for (Employee employee : employees) {
            EmployeeDto employeeDto = employeeMapper.toEmployeeDto(employee);
            objectMapper.readerForUpdating(employeeDto).readValue(patchNode);
            employeeMapper.updateWithNull(employeeDto, employee);
        }

        List<Employee> resultEmployees = employeeRepository.saveAll(employees);
        return resultEmployees.stream()
                .map(Employee::getId)
                .toList();
    }

    public EmployeeDto delete(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    public void deleteMany(List<UUID> ids) {
        employeeRepository.deleteAllById(ids);
    }

}
