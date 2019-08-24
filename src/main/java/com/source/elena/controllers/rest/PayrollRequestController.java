package com.source.elena.controllers.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.source.elena.constant.ConstantUtils;
import com.source.elena.controllers.rest.body.AjaxResponseBody;
import com.source.elena.controllers.rest.body.RestDateWrapper;
import com.source.elena.controllers.rest.body.RestEmployeePayrollDatesWrapper;
import com.source.elena.controllers.rest.body.RestEmployeesRecordsWrapper;
import com.source.elena.entities.Employee;
import com.source.elena.entities.Payroll;
import com.source.elena.entities.PayrollReport;
import com.source.elena.entities.WorkHoursDate;
import com.source.elena.services.EmployeeService;
import com.source.elena.services.PayRollService;
import com.source.elena.services.WorkHoursService;

@RestController
@RequestMapping("/ajax")
public class PayrollRequestController implements ConstantUtils {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PayRollService payRollService;
	
	@Autowired
	private WorkHoursService workHoursService;
	
	@PostMapping(path = "/request/payroll-date.do",
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<?> getCurrentPayrollWeek(@Valid @RequestBody RestDateWrapper wrapper, HttpSession httpSession) {
		AjaxResponseBody<LocalDate, Employee> responseResult = new AjaxResponseBody<>();
		
		List<LocalDate> payrollDates = new ArrayList<>();
		LocalDate dateFrom = LocalDate.parse(wrapper.getDateFrom());
		for (int i = 0; i < DAY_OF_WORK; i++) {
			payrollDates.add(dateFrom);
			dateFrom = dateFrom.plusDays(1);
		}
		
		responseResult.setPayrollDates(payrollDates);
		httpSession.setAttribute(CURRENT_WEEK, payrollDates);
		httpSession.setAttribute(DATE_FROM, payrollDates.get(DATE_FROM_VALUE));
		httpSession.setAttribute(DATE_TO, payrollDates.get(DATE_TO_VALUE));
		
		List<Employee> activeEmployeesFromDateRange = findActiveEmployeesFromDateRange(payrollDates);
		if (activeEmployeesFromDateRange.isEmpty()) {
			List<Employee> activeEmployees = employeeService.findActiveEmployees();
			responseResult.setEmployees(activeEmployees);
			responseResult.setExist(false);
			httpSession.setAttribute(SAFE_SAVING, true);
		} else {
			responseResult.setEmployees(activeEmployeesFromDateRange);
			responseResult.setExist(true);
			httpSession.setAttribute(SAFE_SAVING, false);
		}
		return ResponseEntity.ok(responseResult);
	}

	@PostMapping(path = "/request/post-employee.do",
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<?> postDailyRateAndWorkHours(@Valid @RequestBody RestEmployeesRecordsWrapper wrapper, HttpServletResponse response, HttpSession httpSession) {
		boolean isSafeToSave =  (Boolean) httpSession.getAttribute(SAFE_SAVING);
		if (isSafeToSave) {
			wrapper.getEmployeesRecords().stream().forEach((employee) -> {
				Payroll payroll = new Payroll();
				
				@SuppressWarnings("unchecked")
				List<LocalDate> payrollDates = (List<LocalDate>) httpSession.getAttribute(CURRENT_WEEK);
				LocalDate localDateFrom =  (LocalDate) httpSession.getAttribute(DATE_FROM);
				LocalDate localDateTo = (LocalDate) httpSession.getAttribute(DATE_TO);
				Date utilDateFrom = Date.from(localDateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date utilDateTo = Date.from(localDateTo.atStartOfDay(ZoneId.systemDefault()).toInstant());
				int workHours = employee.getWorkHours()
										.stream()
										.filter(e -> e != null)
										.mapToInt(Integer::intValue)
										.sum();
				
				payroll.setEmployeeId(employee.getEmployeeEID());
				payroll.setOvertimeHours(employee.getOvertimeHours());
				payroll.setUndertimeLate(employee.getUndertimeLate());
				payroll.setCashAdvanced(employee.getCashAdvanced());
				payroll.setDailyRate(employee.getDailyRate());
				payroll.setDateFrom(utilDateFrom);
				payroll.setDateTo(utilDateTo);
				payroll.setTotalWorkHours(workHours);
				
				double grossPay = getGrossRate(employee.getDailyRate(), workHours, employee.getOvertimeHours());
				double netPay = getNetPay(grossPay, employee.getCashAdvanced(), employee.getUndertimeLate());
				
				payroll.setGrossPay(grossPay);
				payroll.setNetPay(netPay);
				
				payRollService.saveEmployee(payroll);
				saveEmployeeWorkHours(employee.getEmployeeEID(), payrollDates, employee.getWorkHours());
			});
		}
		return ResponseEntity.ok("SUCCESSFULLY COMMITED.");
	}
	
	@PostMapping(path = "/request/employee-payroll.do",
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<?> findEmployeePayrollFromDates(@Valid @RequestBody RestEmployeePayrollDatesWrapper wrapper) {
		Date dateFrom = wrapper.getDateFrom();
		Date dateTo = wrapper.getDateTo();
		List<PayrollReport> employeePayroll = employeeService.findEmployeePayroll(dateFrom, dateTo);
		return ResponseEntity.ok(employeePayroll);
	}
	
	private void saveEmployeeWorkHours(String employeeEID, List<LocalDate> payrollDates, List<Integer> workHours) {
		WorkHoursDate workHoursDate = null;
		for (int i = 0; i < DAY_OF_WORK; i++) {
			workHoursDate = new WorkHoursDate();
			
			LocalDate workDate = payrollDates.get(i);
			Optional<Integer> workHoursObject = Optional.ofNullable(workHours.get(i));
			Date utilWorkDate = Date.from(workDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			workHoursDate.setWorkDate(utilWorkDate);
			workHoursDate.setWorkHours(workHoursObject.orElse(0));
			workHoursDate.setEmployeeId(employeeEID);
			workHoursService.save(workHoursDate);
		}
	}

	private List<Employee> findActiveEmployeesFromDateRange(List<LocalDate> payrollDates) {
		LocalDate localDateFrom = payrollDates.get(DATE_FROM_VALUE);
		LocalDate localDateTo = payrollDates.get(DATE_TO_VALUE);
		Date utilDateFrom = Date.from(localDateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date utilDateTo = Date.from(localDateTo.atStartOfDay(ZoneId.systemDefault()).toInstant());
		return payRollService.findActiveEmployeesBetweenDates(utilDateFrom, utilDateTo);
	}
	
	private double getNetPay(double grossPayParam, double cashAdvancedParam, double undertimeLateParam) {
		BigDecimal grossPay = new BigDecimal(grossPayParam);
		BigDecimal cashAdvanced = new BigDecimal(cashAdvancedParam);
		BigDecimal undertimeLate = new BigDecimal(undertimeLateParam);
		
		BigDecimal deduction = cashAdvanced.add(undertimeLate);
		BigDecimal netIncome = grossPay.subtract(deduction);
		return netIncome.doubleValue();
	}

	private double getGrossRate(double dailyRateParam, int workHoursParam, int overtimeParam) {
		BigDecimal dailyRate = new BigDecimal(dailyRateParam);
		BigDecimal workHours = new BigDecimal(workHoursParam);
		BigDecimal overtimeHours = new BigDecimal(overtimeParam);
		BigDecimal dailyWorkHours = new BigDecimal(DAILY_WORK_HOURS);
		
		BigDecimal ratePerHour = dailyRate.divide(dailyWorkHours);
		BigDecimal workHoursPay = ratePerHour.multiply(workHours);
		BigDecimal overtimeHoursPay = ratePerHour.multiply(overtimeHours);
		BigDecimal grossPay = workHoursPay.add(overtimeHoursPay);
		return grossPay.doubleValue();
	}
}
