package com.source.elena.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.source.elena.entities.Employee;
import com.source.elena.entities.Payroll;
import com.source.elena.repositories.PayRollRepository;

@Service
public class PayRollService {

	@Autowired
	private PayRollRepository payRollRepository;
	
	public Optional<Payroll> saveEmployee(Payroll payroll) {
		return Optional.ofNullable(payRollRepository.save(payroll));
	}
	
	public List<Employee> findActiveEmployeesBetweenDates(Date dateFrom, Date dateTo) {
		List<Payroll> employees = payRollRepository.findActiveEmployeesBetweenDates(dateFrom, dateTo);
		return employees.stream().map(employee -> employee.getEmployee()).collect(Collectors.toList());
	}
}
