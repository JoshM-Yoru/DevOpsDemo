package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ticket_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketStatus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="status_id")
	private Integer statusId;
	
	@Column(unique = true)
	private String status;

}
