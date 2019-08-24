package com.source.elena.controllers;

import javax.validation.Valid;

import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.source.elena.entities.Employee;
import com.source.elena.services.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/payroll/calculation", method = RequestMethod.GET)
	public String viewPayrollCalculation() {
		return "payroll_calculation";
	}
	
	@RequestMapping(value = "/employee/registration", method = RequestMethod.GET)
	public String viewAddEmployee(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "add_employee";
	}
	
	@RequestMapping(value = "/payroll/reports", method = RequestMethod.GET)
	public String viewPayrollReport() {
		return "employees_report";
	}
	
	@RequestMapping(value = "/employee/registration", method = RequestMethod.POST)
	public String employeeRegistration(@ModelAttribute @Valid Employee employee, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "add_student";
		}
		
		String firstname = WordUtils.capitalizeFully(employee.getFirstname());
		String lastname = WordUtils.capitalizeFully(employee.getLastname());
		String position = WordUtils.capitalizeFully(employee.getPosition());
		
		employee.setFirstname(firstname);
		employee.setLastname(lastname);
		employee.setPosition(position);
		employeeService.saveEmployee(employee);
		
		Employee newEmployee = new Employee();
		model.addAttribute("employee", newEmployee);
		model.addAttribute("message", "Employee successfully registered.");
		
		return "add_employee";
	}
}
