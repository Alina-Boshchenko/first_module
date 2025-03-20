package ru.boshchenko.projections.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.boshchenko.projections.model.Employee;
import ru.boshchenko.projections.spel.EmployeeProjection;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {


    EmployeeProjection findProjectionById (UUID id);

    List<EmployeeProjection> findAllProjectionsBy();



}