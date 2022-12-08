package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.models.Employee;
import com.example.models.EmployeeRole;
import com.example.models.TicketStatus;
import com.example.models.TicketType;
import com.example.repository.EmployeeRepository;
import com.example.repository.EmployeeRoleRepository;
import com.example.repository.TicketStatusRepository;
import com.example.repository.TicketTypeRepository;

@SpringBootApplication
public class ErsBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErsBootApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner CommandLineRunnerBean(EmployeeRoleRepository err, TicketStatusRepository tsr, TicketTypeRepository ttr, EmployeeRepository er) {
		return (args) -> {
			/*
			List<EmployeeRole> roles = err.findAll();
			if(roles.isEmpty()) {
				System.out.println("We need to load our precreated roles into the table");
				EmployeeRole r1 = new EmployeeRole(0, "EMPLOYEE");
				EmployeeRole r2 = new EmployeeRole(0, "MANAGER");
				err.save(r1);
				err.save(r2);
			}
			
			if(tsr.count() < 1) {
				TicketStatus ts1 = new TicketStatus(0, "PENDING");
				TicketStatus ts2 = new TicketStatus(0, "APPROVED");
				TicketStatus ts3 = new TicketStatus(0, "DENIED");
				
				tsr.save(ts1);
				tsr.save(ts2);
				tsr.save(ts3);
			}
			
			if(ttr.count() < 1) {
				TicketType tt1 = new TicketType(0, "FOOD");
				TicketType tt2 = new TicketType(0, "TRAVEL");
				TicketType tt3 = new TicketType(0, "LODGING");
				TicketType tt4 = new TicketType(0, "OTHER");
				
				ttr.save(tt1);
				ttr.save(tt2);
				ttr.save(tt3);
				ttr.save(tt4);
			}
			
			if(er.count() < 1) {
				List<EmployeeRole> eRoles = new ArrayList<>();
				eRoles.add(err.getById(2));
				Employee manager = new Employee(0, eRoles, "Ethan", "McGill", "ethan@email.com", "password", new ArrayList<>(), new ArrayList<>());
				er.save(manager);
			}
			*/
		};
	}

}
