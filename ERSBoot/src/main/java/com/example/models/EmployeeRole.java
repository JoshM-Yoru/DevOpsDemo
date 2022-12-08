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
@Table(name="employee_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRole {

	@Id
	@Column(name="role_id")
	private Integer roleId;
	
	@Column(unique=true)
	private String role;
	
}
