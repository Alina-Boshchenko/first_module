package ru.boshchenko.projections.rest;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.projections.dto.DepartmentDto;
import ru.boshchenko.projections.service.DepartmentServiceImpl;
import java.util.UUID;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentRest {

    private final DepartmentServiceImpl departmentServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(departmentServiceImpl.getOne(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> create(@RequestBody @Valid DepartmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentServiceImpl.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DepartmentDto> patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentServiceImpl.patch(id, patchNode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDto> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(departmentServiceImpl.delete(id));
    }

}
