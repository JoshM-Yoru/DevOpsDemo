package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.NotAManagerException;
import com.example.models.Employee;
import com.example.models.Ticket;
import com.example.models.TicketStatus;
import com.example.models.TicketType;
import com.example.repository.EmployeeRepository;
import com.example.repository.TicketRepository;
import com.example.repository.TicketStatusRepository;
import com.example.repository.TicketTypeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class TicketService {

	private TicketRepository tRepo;
	private TicketStatusRepository tsRepo;
	private TicketTypeRepository ttRepo;
	private EmployeeRepository eRepo;
	
	public Ticket createTicket(TicketType type, String description, Double amount, String email, String date) {
		TicketType t = ttRepo.findById(type.getTypeId()).get();
		TicketStatus s = tsRepo.findById(1).get();
		Employee e = eRepo.getByEmail(email).get();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate time = LocalDate.parse(date, format);
		Ticket ticket = new Ticket(t, s, description, amount, e, time);
		return tRepo.save(ticket);
	}
	
	public Ticket approveDenyTicket(int manager, int id, boolean approved) {
		Employee approver = eRepo.findById(manager).get();
		System.out.println(approver);
		if(approver.getRole().get(0).getRole().equals("MANAGER")) {
			Ticket t = tRepo.findById(id).get();
			if(!t.getStatus().get(0).getStatus().equals("PENDING")) return t;
			TicketStatus ts = approved ? tsRepo.findById(2).get() : tsRepo.findById(3).get();
			List<TicketStatus> status = t.getStatus();
			t.setReviewer(approver);
			t.setReviewDate(LocalDate.now());
			status.set(0, ts);
			return tRepo.save(t);
		}
		
		throw new NotAManagerException();
	}
	
	public List<Ticket> getPendingTickets(int manager) {
		Employee m = eRepo.findById(manager).get();
		if(m.getRole().get(0).getRole().equals("MANAGER")) {
			TicketStatus s = tsRepo.findById(1).get();
			return tRepo.getTicketsByStatus(s);
		}
		
		throw new NotAManagerException();
	}
	
	public List<Ticket> getEmployeeTickets(int id){
		Employee e = eRepo.findById(id).get();
		
		return tRepo.getTicketsBySubmitter(e);
	}
	
}
