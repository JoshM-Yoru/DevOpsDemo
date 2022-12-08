package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Ticket;
import com.example.models.TicketType;
import com.example.service.TicketService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tickets")
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class TicketController {
	
	private TicketService tService;
	
	@PostMapping("/")
	public Ticket createTicket(@RequestBody NewTicketObject body) {
		return tService.createTicket(body.type, body.description, body.amount, body.email, body.date);
	}
	
	@PutMapping("/")
	public Ticket approveOrDeny(@RequestBody UpdateTicketObject body) {
		return tService.approveDenyTicket(body.managerId, body.ticketId, body.approved);
	}
	
	@GetMapping("/{role}")
	public List<Ticket> getTickets(@PathVariable("role")String role, @RequestParam("id")int id){
		if(role.equals("manager")) {
			//Return the pending tickets
			return tService.getPendingTickets(id);
		}
		else {
			//return all the employees tickets
			return tService.getEmployeeTickets(id);
		}
	}

}

class NewTicketObject {
	public TicketType type;
	public String description;
	public Double amount;
	public String email;
	public String date;
}

class UpdateTicketObject {
	public int managerId;
	public int ticketId;
	public boolean approved;
}