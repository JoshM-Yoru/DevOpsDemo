package com.example.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employee_id")
	private Integer employeeId;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="employee_role_junction",
			joinColumns = {@JoinColumn(name="employee_id")},
			inverseJoinColumns = {@JoinColumn(name="role")}
	)
	private List<EmployeeRole> role;

	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	//List of reimbursements
	@OneToMany(mappedBy="submitter", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Ticket> reimbursements;
	
	
	//List of approved reimbursements
	@OneToMany(mappedBy="reviewer", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Ticket> reviewedReimbursements;


	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", role=" + role.get(0) + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", password=" + password + ", reimbursements=" + reimbursements.size()
				+ ", reviewedReimbursements=" + reviewedReimbursements.size() + "]";
	}
	
	
}
