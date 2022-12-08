package com.example.controllers;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.EmailAlreadyExistsException;
import com.example.exceptions.InvalidCredentialsException;
import com.example.models.Employee;
import com.example.models.RegisterObject;
import com.example.service.EmployeeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class EmployeeController {
	
	private EmployeeService eService;
	
	@PostMapping("/register")
	public Employee register(@RequestBody RegisterObject ro) {
		System.out.println(ro);
		return eService.registerEmployee(ro.firstName, ro.lastName, ro.email, ro.password);
	}
	
	@PostMapping("/login")
	public Employee login(@RequestBody LinkedHashMap<String, String> body) {
		
		String email = body.get("email");
		String password = body.get("password");
		
		return eService.loginEmployee(email, password);
	}
	
	@ExceptionHandler({InvalidCredentialsException.class})
	public ResponseEntity<String> handleInvalid(){
		return new ResponseEntity<>("Invalid Credentials", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler({EmailAlreadyExistsException.class})
	public ResponseEntity<String> handleExists(){
		return new ResponseEntity<>("Email already registered", HttpStatus.CONFLICT);
	}
}
