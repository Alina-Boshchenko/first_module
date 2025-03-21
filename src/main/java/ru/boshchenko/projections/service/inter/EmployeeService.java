package ru.boshchenko.projections.service.inter;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.boshchenko.projections.dto.EmployeeDto;
import ru.boshchenko.projections.spel.EmployeeProjection;

import java.util.List;
import java.util.UUID;

@Service
public interface EmployeeService {

    EmployeeProjection findProjectionById(UUID id);

    List<EmployeeProjection> findAllProjectionsBy();

    Page<EmployeeDto> getAll(Pageable pageable);

    EmployeeDto getOne(UUID id);

    List<EmployeeDto> getMany(List<UUID> ids);

    EmployeeDto create(EmployeeDto dto);

    EmployeeDto patch(UUID id, JsonNode patchNode);

    List<UUID> patchMany(List<UUID> ids, JsonNode patchNode);

    EmployeeDto delete(UUID id);

    void deleteMany(List<UUID> ids);
}
