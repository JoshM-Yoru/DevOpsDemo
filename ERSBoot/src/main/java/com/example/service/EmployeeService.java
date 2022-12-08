package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exceptions.EmailAlreadyExistsException;
import com.example.exceptions.InvalidCredentialsException;
import com.example.models.Employee;
import com.example.models.EmployeeRole;
import com.example.repository.EmployeeRepository;
import com.example.repository.EmployeeRoleRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class EmployeeService {
	
	private EmployeeRepository eRepo; 
	private EmployeeRoleRepository erRepo;
	
	public Employee registerEmployee(String firstName, String lastName, String email, String password) {
		EmployeeRole role = erRepo.findById(1).get();
		List<EmployeeRole> roles = new ArrayList<>();
		roles.add(role);
		Employee e = new Employee(0, roles, firstName, lastName, email, password, new ArrayList<>(), new ArrayList<>());
		
		try {
			Employee ret = eRepo.save(e);
			return ret;
		} catch(Exception ex) {
			throw new EmailAlreadyExistsException();
		}
	}
	
	public Employee loginEmployee(String email, String password) {
		Employee e = eRepo.getByEmail(email).orElseThrow(InvalidCredentialsException::new);
		
		if(!e.getPassword().equals(password)) {
			throw new InvalidCredentialsException();
		}
		
		return e;
		
	}

}
