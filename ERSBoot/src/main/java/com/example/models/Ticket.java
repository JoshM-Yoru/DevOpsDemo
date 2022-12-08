package com.example.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ticket_id")
	private Integer ticketId;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="ticket_type_junction",
			joinColumns = {@JoinColumn(name="ticket_id")},
			inverseJoinColumns = {@JoinColumn(name="type")}
	)
	private List<TicketType> type;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="ticket_status_junction",
			joinColumns = {@JoinColumn(name="ticket_id")},
			inverseJoinColumns = {@JoinColumn(name="status")}
	)
	private List<TicketStatus> status;
	
	private String description;
	
	private Double amount;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="submitter_id")
	private Employee submitter;
	
	private LocalDate submittedDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="reviewer_id")
	private Employee reviewer;
	
	private LocalDate reviewDate;
	
	public Ticket(TicketType type, TicketStatus status, String description, Double amount, Employee submitter, LocalDate submittedDate) {
		this.type = new ArrayList<>();
		this.type.add(type);
		this.status = new ArrayList<>();
		this.status.add(status);
		this.description = description;
		this.amount = amount;
		this.submitter = submitter;
		this.submittedDate = submittedDate;
		this.reviewer = null;
		this.reviewDate = null;
	}

}
