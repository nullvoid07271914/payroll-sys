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
@Table(name = "payroll_tbl")
public class Payroll {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payroll_sequence")
    @GenericGenerator(
        name = "payroll_sequence",
        strategy = "com.source.elena.entities.SequenceStringIDGenerator", 
        parameters = {
            @Parameter(name = SequenceStringIDGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = SequenceStringIDGenerator.VALUE_PREFIX_PARAMETER, value = "PT-"),
            @Parameter(name = SequenceStringIDGenerator.NUMBER_FORMAT_PARAMETER, value = "%08d") })
	@Column(name = "payroll_id")
	private String payrollId;
	
	@Column(name = "date_from")
	@Temporal(TemporalType.DATE)
	private Date dateFrom;
	
	@Column(name = "date_to")
	@Temporal(TemporalType.DATE)
	private Date dateTo;
	
	@Column(name = "total_work_hours")
	private int totalWorkHours;
	
	@Column(name = "overtime_hours")
	private int overtimeHours;
	
	@Column(name = "daily_rate")
	private double dailyRate;
	
	@Column(name = "cash_advanced")
	private double cashAdvanced;
	
	@Column(name = "undertime_late")
	private double undertimeLate;
	
	@Column(name = "gross_pay")
	private double grossPay;
	
	@Column(name = "net_pay")
	private double netPay;
	
	@Column(name = "employee_id")
	private String employeeId;
	
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", insertable = false, updatable = false)
	private Employee employee;

	public String getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(String payrollId) {
		this.payrollId = payrollId;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public int getTotalWorkHours() {
		return totalWorkHours;
	}

	public void setTotalWorkHours(int totalWorkHours) {
		this.totalWorkHours = totalWorkHours;
	}

	public int getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(int overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public double getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(double dailyRate) {
		this.dailyRate = dailyRate;
	}

	public double getCashAdvanced() {
		return cashAdvanced;
	}

	public void setCashAdvanced(double cashAdvanced) {
		this.cashAdvanced = cashAdvanced;
	}

	public double getUndertimeLate() {
		return undertimeLate;
	}

	public void setUndertimeLate(double undertimeLate) {
		this.undertimeLate = undertimeLate;
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
		return "Payroll [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", totalWorkHours=" + totalWorkHours + ", overtimeHours="
				+ overtimeHours + ", dailyRate=" + dailyRate + ", cashAdvanced=" + cashAdvanced + ", undertimeLate="
				+ undertimeLate + ", grossPay=" + grossPay + ", netPay=" + netPay + ", employeeId=" + employeeId + "]";
	}
}