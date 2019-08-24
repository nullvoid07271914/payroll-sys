package com.source.elena.controllers.rest.body;

import java.util.List;

public class EmployeeWorkHours {

	private double dailyRate;
	
	private List<Integer> workHours;
	
	private String employeeEID;
	
	private double cashAdvanced;
	
	private int overtimeHours;
	
	private double undertimeLate;

	public double getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(double dailyRate) {
		this.dailyRate = dailyRate;
	}

	public List<Integer> getWorkHours() {
		return workHours;
	}

	public void setWorkHours(List<Integer> workHours) {
		this.workHours = workHours;
	}

	public String getEmployeeEID() {
		return employeeEID;
	}

	public void setEmployeeEID(String employeeEID) {
		this.employeeEID = employeeEID;
	}

	public double getCashAdvanced() {
		return cashAdvanced;
	}

	public void setCashAdvanced(double cashAdvanced) {
		this.cashAdvanced = cashAdvanced;
	}

	public int getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(int overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public double getUndertimeLate() {
		return undertimeLate;
	}

	public void setUndertimeLate(double undertimeLate) {
		this.undertimeLate = undertimeLate;
	}

	@Override
	public String toString() {
		return "RestEmployeeWorkHoursWrapper [dailyRate=" + dailyRate + ", workHours=" + workHours + ", employeeEID="
				+ employeeEID + ", cashAdvanced=" + cashAdvanced + ", overtimeHours=" + overtimeHours
				+ ", undertimeLate=" + undertimeLate + "]";
	}
}
