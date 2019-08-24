package com.source.elena.repositories.custom;

import java.util.List;

import com.source.elena.entities.Employee;

public interface EmployeeQueryTypedBuilder {

	public List<Employee> findActiveEmployees();
}
