package ru.boshchenko.projections.mapper;

import org.mapstruct.*;
import ru.boshchenko.projections.dto.DepartmentDto;
import ru.boshchenko.projections.model.Department;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {

    Department toEntity(DepartmentDto departmentDto);

    DepartmentDto toDepartmentDto(Department department);

    @InheritConfiguration(name = "toEntity")
    Department updateWithNull(DepartmentDto departmentDto, @MappingTarget Department department);

}