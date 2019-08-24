package com.source.elena.controllers.rest.body;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestEmployeePayrollDatesWrapper {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	private Date dateFrom;
	
	private Date dateTo;
	
	public RestEmployeePayrollDatesWrapper(Date dateFrom, Date dateTo) {
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	
	public RestEmployeePayrollDatesWrapper(String dateFrom, String dateTo) {
		try {
			this.dateFrom = formatter.parse(dateFrom);
			this.dateTo = formatter.parse(dateTo);
		} catch (ParseException e) {
			System.err.println("RestEmployeePayrollDatesWrapper -> " + e.getMessage());
		}
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
}
