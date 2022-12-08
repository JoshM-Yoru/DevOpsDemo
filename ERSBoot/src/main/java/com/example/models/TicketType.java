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
@Table(name="ticket_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketType {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="type_id")
	private Integer typeId;
	
	@Column(unique = true)
	private String type;

}
