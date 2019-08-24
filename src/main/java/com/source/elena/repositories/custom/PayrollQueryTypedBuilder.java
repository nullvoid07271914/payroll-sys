package com.source.elena.repositories.custom;

import java.util.Date;
import java.util.List;

import com.source.elena.entities.Payroll;

public interface PayrollQueryTypedBuilder {

	public List<Payroll> findActiveEmployeesBetweenDates(Date dateFrom, Date dateTo);
}
