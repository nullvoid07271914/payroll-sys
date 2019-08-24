package com.source.elena.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.source.elena.entities.Employee;
import com.source.elena.entities.PayrollReport;
import com.source.elena.repositories.custom.BaseRepository;
import com.source.elena.repositories.custom.EmployeeQueryTypedBuilder;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, String>, EmployeeQueryTypedBuilder {

	@Query(value = "SELECT new com.source.elena.entities.PayrollReport(" + 
			"	EMPLOYEE.employee_id, " + 
			"	EMPLOYEE.firstname, " + 
			"	EMPLOYEE.lastname, " + 
			"	EMPLOYEE.position, " + 
			"	PAY.payroll_id, " + 
			"	PAY.cash_advanced, " + 
			"	PAY.daily_rate, " + 
			"	PAY.gross_pay, " + 
			"	PAY.net_pay, " + 
			"	PAY.overtime_hours, " + 
			"	PAY.undertime_late, " + 
			"	PAY.total_work_hours, " + 
			"	WORKS.work_date, " + 
			"	WORKS.work_hours) " + 
			"	FROM " + 
			"		(SELECT * FROM employee_tbl WHERE status = 1) EMPLOYEE, " + 
			"		(SELECT * FROM payroll_tbl WHERE date_from = DATE('2019-08-26') AND date_to = DATE('2019-08-31')) PAY, " + 
			"		(SELECT * FROM work_hours_date_tbl WHERE work_date >= DATE('2019-08-26') AND work_date <= DATE('2019-08-31')) WORKS " + 
			"	WHERE " + 
			"		EMPLOYEE.employee_id = PAY.employee_id " + 
			"		AND PAY.employee_id = WORKS.employee_id " + 
			"			ORDER BY EMPLOYEE.lastname ASC", nativeQuery = true)
	public List<PayrollReport> findEmployeePayroll(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
}
