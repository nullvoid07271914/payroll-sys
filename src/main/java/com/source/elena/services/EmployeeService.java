package com.source.elena.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.source.elena.entities.Employee;
import com.source.elena.entities.PayrollReport;
import com.source.elena.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Optional<Employee> saveEmployee(Employee employee) {
		return Optional.ofNullable(employeeRepository.save(employee));
	}
	
	@Transactional(readOnly = true)
	public List<Employee> findActiveEmployees() {
		return employeeRepository.findActiveEmployees();
	}
	
	@Transactional(readOnly = true)
	public List<PayrollReport> findEmployeePayroll(Date dateFrom, Date dateTo) {
		return employeeRepository.findEmployeePayroll(dateTo, dateTo);
	}
}
