package com.source.elena.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "work_hours_date_tbl")
public class WorkHoursDate {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hours_date_sequence")
    @GenericGenerator(
        name = "hours_date_sequence",
        strategy = "com.source.elena.entities.SequenceStringIDGenerator", 
        parameters = {
            @Parameter(name = SequenceStringIDGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = SequenceStringIDGenerator.VALUE_PREFIX_PARAMETER, value = "EID-"),
            @Parameter(name = SequenceStringIDGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d") })
	@Column(name = "work_hours_id")
	private String workHoursId;
	
	@Column(name = "work_hours")
	private int workHours;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "work_date")
	private Date workDate;
	
	@Column(name = "employee_id")
	private String employeeId;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", insertable = false, updatable = false)
	private Employee employee;

	public String getWorkHoursId() {
		return workHoursId;
	}

	public void setWorkHoursId(String workHoursId) {
		this.workHoursId = workHoursId;
	}

	public int getWorkHours() {
		return workHours;
	}

	public void setWorkHours(int workHours) {
		this.workHours = workHours;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "WorkHoursDate [workHoursId=" + workHoursId + ", workHours=" + workHours + ", workDate=" + workDate
				+ ", employeeId=" + employeeId + ", employee=" + employee + "]";
	}
}
