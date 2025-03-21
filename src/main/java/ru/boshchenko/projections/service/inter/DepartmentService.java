package ru.boshchenko.projections.service.inter;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.boshchenko.projections.dto.DepartmentDto;
import ru.boshchenko.projections.exception.ResourceNotFoundException;
import ru.boshchenko.projections.model.Department;

import java.util.UUID;

@Service
public interface DepartmentService {

    public DepartmentDto getOne(UUID id);

    public DepartmentDto create(DepartmentDto dto);

    public DepartmentDto patch(UUID id, JsonNode patchNode);

    public DepartmentDto delete(UUID id);

}
