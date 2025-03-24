package ru.boshchenko.projections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.boshchenko.projections.model.Role;

import java.util.UUID;

@SpringBootApplication
public class ProjectionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectionsApplication.class, args);
	}

}
