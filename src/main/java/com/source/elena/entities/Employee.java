package com.source.elena.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "employee_tbl")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @GenericGenerator(
        name = "employee_sequence",
        strategy = "com.source.elena.entities.SequenceStringIDGenerator", 
        parameters = {
            @Parameter(name = SequenceStringIDGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = SequenceStringIDGenerator.VALUE_PREFIX_PARAMETER, value = "EID-"),
            @Parameter(name = SequenceStringIDGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d") })
	@Column(name = "employee_id")
	private String employeeId;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "status")
	private boolean active;
	
	@JsonBackReference
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "employee_id", nullable = true, insertable = false)
	private List<Payroll> payrolls;
	
	@JsonBackReference
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "employee_id", nullable = true, insertable = false)
	private List<WorkHoursDate> workHoursDate;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Payroll> getPayrolls() {
		return payrolls;
	}

	public void setPayrolls(List<Payroll> payrolls) {
		this.payrolls = payrolls;
	}

	public List<WorkHoursDate> getWorkHoursDate() {
		return workHoursDate;
	}

	public void setWorkHoursDate(List<WorkHoursDate> workHoursDate) {
		this.workHoursDate = workHoursDate;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", position=" + position + ", active=" + active + ", payrolls=" + payrolls + "]";
	}
}
