package ru.boshchenko.projections.mapper;

import org.mapstruct.*;
import ru.boshchenko.projections.dto.EmployeeDto;
import ru.boshchenko.projections.model.Employee;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    @Mapping(source = "departmentId", target = "department.id")
    Employee toEntity(EmployeeDto employeeDto);

    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDto toEmployeeDto(Employee employee);

    @InheritConfiguration(name = "toEntity")
    Employee updateWithNull(EmployeeDto employeeDto, @MappingTarget Employee employee);
}