package ru.boshchenko.projections.spel;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Value;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface EmployeeProjection {

    @Value("#{target.lastName + ' ' + target.firstName}")
    String getFullName();

    @Value("#{target.department.name}")
    String getDepartmentName();

    String getPosition();
}