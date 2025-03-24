package ru.boshchenko.projections.rest;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.boshchenko.projections.dto.EmployeeDto;
import ru.boshchenko.projections.service.EmployeeServiceImpl;
import ru.boshchenko.projections.spel.EmployeeProjection;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeRest {

    private final EmployeeServiceImpl employeeServiceImpl;

    @GetMapping("/projection/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmployeeProjection> findProjectionById(@PathVariable UUID id){
        return ResponseEntity.ok(employeeServiceImpl.findProjectionById(id));
    }

    @GetMapping("/projection")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<EmployeeProjection>> findAllProjectionsBy(){
        return ResponseEntity.ok(employeeServiceImpl.findAllProjectionsBy());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<EmployeeDto>> getAll(@PageableDefault(sort = "lastName") Pageable pageable) {
        Page<EmployeeDto> data = employeeServiceImpl.getAll(pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EmployeeDto> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeServiceImpl.getOne(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody @Valid EmployeeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeServiceImpl.create(dto));
    }

    // доп

    @GetMapping("/by-ids")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<EmployeeDto>> getMany(@RequestParam List<UUID> ids) {
        return ResponseEntity.ok(employeeServiceImpl.getMany(ids));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeServiceImpl.patch(id, patchNode));
    }

    @PatchMapping
    public ResponseEntity<List<UUID>> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeServiceImpl.patchMany(ids, patchNode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDto> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeServiceImpl.delete(id));
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        employeeServiceImpl.deleteMany(ids);
    }
}
