package com.example.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.example.ErsBootApplication;
import com.example.models.Employee;
import com.example.models.EmployeeRole;
import com.example.models.RegisterObject;
import com.example.repository.EmployeeRepository;
import com.example.repository.EmployeeRoleRepository;
import com.example.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//Tell Spring boot to spin up a fake server/test server and load all of our normal application context onto that server
//Aka use a testing server, but setup all our beans as normal
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes= ErsBootApplication.class)
//Setup our internal API routes with the mocked server
@AutoConfigureMockMvc
//Setup our testing H2 database
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class EmployeeControllerIntegrationTests {
	
	//Setup a mockMvc object which allows us to generate API calls
	@Autowired
	private MockMvc mockMvc;
	
	//We want to look inside our database after each test, so lets pull in the EmployeeRepository
	@Autowired
	private EmployeeRepository eRepo;
	
	@Autowired
	private EmployeeRoleRepository erRepo;
	
	@Autowired
	private EmployeeService eServ;
	
	private ObjectMapper om = new ObjectMapper();
	
	
	//We want to be sure our database is clear after each test, so lets setup a @BeforeEach method to clear the database
	@BeforeEach
	public void resetDatabase() {
		System.out.println("Run before each test");
		eRepo.deleteAll();
		List<EmployeeRole> roles = erRepo.findAll();
		System.out.println(roles);
		if(roles.isEmpty()) {
			System.out.println("We need to load our precreated roles into the table");
			EmployeeRole r1 = new EmployeeRole(1, "EMPLOYEE");
			EmployeeRole r2 = new EmployeeRole(2, "MANAGER");
			erRepo.save(r1);
			erRepo.save(r2);
			roles = erRepo.findAll();
			System.out.println(roles);
		}
	}
	
	@Test
	@Transactional
	public void testSuccessfulRegistration() throws Exception {
		//Setup the data to be sent in the API request
		RegisterObject ro = new RegisterObject();
		ro.firstName = "testFirst";
		ro.lastName = "testLast";
		ro.email = "test@mail.com";
		ro.password = "testPass";
		
		String context = om.writeValueAsString(ro);
		
		List<EmployeeRole> roles = new ArrayList<>();
		EmployeeRole emp = new EmployeeRole(1, "EMPLOYEE");
		roles.add(emp);
		
		//Use the mockMvc object we autowired earlier to make a request to our fake server
		MvcResult result = mockMvc.perform(post("/employees/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(context))
		.andDo(print())
		.andExpect(jsonPath("$.firstName").value("testFirst"))
		.andExpect(jsonPath("$.lastName").value("testLast"))
		.andExpect(jsonPath("$.email").value("test@mail.com"))
		.andExpect(jsonPath("$.password").value("testPass"))
		.andExpect(jsonPath("$.employeeId").value(1))
		.andReturn();
		
		Employee resEmployee = om.readValue(result.getResponse().getContentAsString(), Employee.class);
		assertEquals(roles, resEmployee.getRole());
		
		//Check to see if the user was acutally stored in the test db
		Employee registered = eRepo.findById(1).get();
		
		assertNotNull(registered);
		assertEquals("testFirst", registered.getFirstName());
		assertEquals("testLast", registered.getLastName());
		assertEquals("test@mail.com", registered.getEmail());
		assertEquals("testPass", registered.getPassword());
		assertEquals(roles, registered.getRole());
	}
	
	@Test
	@Transactional
	public void testUnsuccessfulRegistration() throws Exception {
		RegisterObject ro = new RegisterObject();
		ro.firstName = "testFirst";
		ro.lastName = "testLast";
		ro.email = "test@mail.com";
		ro.password = "testPass";
		
		eServ.registerEmployee("testFirst", "testLast", "test@mail.com", "testPass");
		
		//eRepo.save(new Employee(1, roles, "testFirst", "testLast", "test@mail.com", "testPass", new ArrayList<>(), new ArrayList<>()));
		
		//Make the register request, except the user is already in the database, so it should cause a 409 Conflict.
		mockMvc.perform(post("/employees/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(ro)))
		.andDo(print())
		.andExpect(status().isConflict())
		.andExpect(content().string("Email already registered"));
	}

}
