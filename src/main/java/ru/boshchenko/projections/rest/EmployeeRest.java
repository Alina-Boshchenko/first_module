package ru.boshchenko.projections.rest;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.projections.dto.EmployeeDto;
import ru.boshchenko.projections.service.EmployeeService;
import ru.boshchenko.projections.spel.EmployeeProjection;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeRest {

    private final EmployeeService employeeService;

    @GetMapping("/projection/{id}")
    public ResponseEntity<EmployeeProjection> findProjectionById(@PathVariable UUID id){
        return ResponseEntity.ok(employeeService.findProjectionById(id));
    }

    @GetMapping("/projection")
    public ResponseEntity<List<EmployeeProjection>> findAllProjectionsBy(){
        return ResponseEntity.ok(employeeService.findAllProjectionsBy());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<EmployeeDto>> getAll(@PageableDefault(sort = "lastName") Pageable pageable) {
        Page<EmployeeDto> data = employeeService.getAll(pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody @Valid EmployeeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(dto));
    }

    // доп

    @GetMapping("/by-ids")
    public ResponseEntity<List<EmployeeDto>> getMany(@RequestParam List<UUID> ids) {
        return ResponseEntity.ok(employeeService.getMany(ids));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.patch(id, patchNode));
    }

    @PatchMapping
    public ResponseEntity<List<UUID>> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeService.patchMany(ids, patchNode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDto> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.delete(id));
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        employeeService.deleteMany(ids);
    }
}
