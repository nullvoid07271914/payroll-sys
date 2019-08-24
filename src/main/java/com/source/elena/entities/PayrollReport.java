package com.source.elena.entities;

import java.util.Date;

public class PayrollReport {

	private String employeeId;

	private String firstname;

	private String lastname;

	private String position;

	private String payrollId;

	private double cashAdvanced;

	private double dailyRate;

	private double grossPay;

	private double netPay;

	private int overtimeHours;

	private double undertimeLate;

	private int totalWorkHours;

	private Date workDate;

	private int workHours;

	public PayrollReport(String employeeId, String firstname, String lastname, String position, String payrollId,
			double cashAdvanced, double dailyRate, double grossPay, double netPay, int overtimeHours,
			double undertimeLate, int totalWorkHours, Date workDate, int workHours) {
		this.employeeId = employeeId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.position = position;
		this.payrollId = payrollId;
		this.cashAdvanced = cashAdvanced;
		this.dailyRate = dailyRate;
		this.grossPay = grossPay;
		this.netPay = netPay;
		this.overtimeHours = overtimeHours;
		this.undertimeLate = undertimeLate;
		this.totalWorkHours = totalWorkHours;
		this.workDate = workDate;
		this.workHours = workHours;
	}

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

	public String getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}

	public double getCashAdvanced() {
		return cashAdvanced;
	}

	public void setCashAdvanced(double cashAdvanced) {
		this.cashAdvanced = cashAdvanced;
	}

	public double getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(double dailyRate) {
		this.dailyRate = dailyRate;
	}

	public double getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(double grossPay) {
		this.grossPay = grossPay;
	}

	public double getNetPay() {
		return netPay;
	}

	public void setNetPay(double netPay) {
		this.netPay = netPay;
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

	public int getTotalWorkHours() {
		return totalWorkHours;
	}

	public void setTotalWorkHours(int totalWorkHours) {
		this.totalWorkHours = totalWorkHours;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public int getWorkHours() {
		return workHours;
	}

	public void setWorkHours(int workHours) {
		this.workHours = workHours;
	}
}
