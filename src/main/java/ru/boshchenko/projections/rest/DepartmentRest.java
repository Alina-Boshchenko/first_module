package ru.boshchenko.projections.rest;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.projections.dto.DepartmentDto;
import ru.boshchenko.projections.service.DepartmentService;
import java.util.UUID;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentRest {

    private final DepartmentService departmentService;

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(departmentService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> create(@RequestBody @Valid DepartmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DepartmentDto> patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(departmentService.patch(id, patchNode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDto> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(departmentService.delete(id));
    }

}
