package com.source.elena.controllers.rest.body;

import java.util.List;

public class RestEmployeesRecordsWrapper {

	private List<EmployeeWorkHours> employeesRecords;

	public List<EmployeeWorkHours> getEmployeesRecords() {
		return employeesRecords;
	}

	public void setEmployeesRecords(List<EmployeeWorkHours> employeesRecords) {
		this.employeesRecords = employeesRecords;
	}
}
