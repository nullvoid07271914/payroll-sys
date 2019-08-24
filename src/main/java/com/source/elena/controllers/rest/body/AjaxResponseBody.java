package com.source.elena.controllers.rest.body;

import java.util.List;

public class AjaxResponseBody<T, E> {

	private String message;
	
	private List<T> payrollDates;
	
	private List<E> employees;
	
	private boolean exist;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getPayrollDates() {
		return payrollDates;
	}

	public void setPayrollDates(List<T> payrollDates) {
		this.payrollDates = payrollDates;
	}

	public List<E> getEmployees() {
		return employees;
	}

	public void setEmployees(List<E> employees) {
		this.employees = employees;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}
}