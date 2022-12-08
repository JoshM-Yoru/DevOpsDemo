package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
	//We already have crud operations, now we can add any special methods we may need
	Optional<Employee> getByEmail(String email);

	
}
