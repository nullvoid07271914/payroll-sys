$(function() {
		$('#payroll_date_btn').click(function() {
			$('tbody#tbody_list').empty();
			
			var options = {};
			var search = {};
			
			search["dateFrom"] = $("#date_from").val();
			
			options.url = "http://localhost:8080/elena-syspay/ajax/request/payroll-date.do";
	        options.dataType = "JSON";
	        options.contentType = "application/json";
	       	options.type = "POST";
	       	options.cache = false;
	        options.data = JSON.stringify(search);
	        options.success = function(data) {
	        	$('table#table-list thead td').each(function(index, item) {
	    			if (index >= 4 && index <= 9) {
	    				var text = $(this).html();
	    				text = text.split(' ')[0];
	    				
	    				var dayOfMonth = '';
	    				var result = data['payrollDates'];
	    	        	for (var i = 0; i < result.length; i++) {
	    	        		var dayOfWeek = result[i]['dayOfWeek'].substr(0, 3);
	    	        		if (dayOfWeek == text) {
	    	        			dayOfMonth = result[i]['dayOfMonth'].toString();
	    	        			if (dayOfMonth.length == 1)
	    	        				dayOfMonth = '0' + dayOfMonth;
	    	        			break;
	    	        		}
	    	        	}
	    				$(this).html(text + ' ' + dayOfMonth);
	    			}
	    		});
	        	
	        	var row = '';
	        	var employees = data['employees'];
	        	for (var i = 0; i < employees.length; i++) {
	        		//if (data['exist'])
	        			//row = '<tr id=""><td>' + employees[i].firstname + '</td><td>' + employees[i].lastname + '</td><td>' + employees[i].position + '</td><td>Php 0.00</td><td class="td-date">' + employees[i].workHoursDate[0].workHours + '</td><td class="td-date"></td><td class="td-date"></td><td class="td-date"></td><td class="td-date"></td><td class="td-date"></td><td>0</td><td style="font-weight:bold;">Php 0.00</td><td>Php 0.00</td><td></td><td>Php 0.00</td><td style="font-weight:bold;">Php 0.00</td><td style="display:none;">' + employees[i].employeeId + '</td><td name="buttons"><div class="btn-group pull-right"><button id="bEdit" type="button" class="btn btn-sm btn-default" onclick="rowEdit(this);" style="display: block;"><span class="glyphicon glyphicon-pencil"> </span></button><button id="bElim" type="button" class="btn btn-sm btn-default" onclick="rowElim(this);" style="display: block;"><span class="glyphicon glyphicon-trash"> </span></button><button id="bAcep" type="button" class="btn btn-sm btn-default" style="display: none;" onclick="rowAcep(this);"><span class="glyphicon glyphicon-ok"> </span></button><button id="bCanc" type="button" class="btn btn-sm btn-default" style="display: none;" onclick="rowCancel(this);"><span class="glyphicon glyphicon-remove"> </span></button></div></td></tr>';
	        		//else
	        			row = '<tr id=""><td>' + employees[i].firstname + '</td><td>' + employees[i].lastname + '</td><td>' + employees[i].position + '</td><td>Php 0.00</td><td class="td-date"></td><td class="td-date"></td><td class="td-date"></td><td class="td-date"></td><td class="td-date"></td><td class="td-date"></td><td>0</td><td style="font-weight:bold;">Php 0.00</td><td>Php 0.00</td><td></td><td>Php 0.00</td><td style="font-weight:bold;">Php 0.00</td><td style="display:none;">' + employees[i].employeeId + '</td><td name="buttons"><div class="btn-group pull-right"><button id="bEdit" type="button" class="btn btn-sm btn-default" onclick="rowEdit(this);" style="display: block;"><span class="glyphicon glyphicon-pencil"> </span></button><button id="bElim" type="button" class="btn btn-sm btn-default" onclick="rowElim(this);" style="display: block;"><span class="glyphicon glyphicon-trash"> </span></button><button id="bAcep" type="button" class="btn btn-sm btn-default" style="display: none;" onclick="rowAcep(this);"><span class="glyphicon glyphicon-ok"> </span></button><button id="bCanc" type="button" class="btn btn-sm btn-default" style="display: none;" onclick="rowCancel(this);"><span class="glyphicon glyphicon-remove"> </span></button></div></td></tr>';
	        		
	        		$('table#table-list tbody').append(row);
	        	}
	        };
	        
			$.ajax(options);
		});
		
		$('#save_btn').click(function() {
			var pattern = '[0-9.]+';
			var options = {};
			var data = {
				'employeesRecords': []
			};
			
			$('table#table-list > tbody > tr').each(function(index) {
				var postData = {
			    	'dailyRate': 0,
			    	'workHours': [],
			    	'cashAdvanced': 0,
			    	'overtimeHours': 0,
			    	'undertimeLate': 0,
			    	'employeeEID': ''
			    };
				
				var daily_rate = $(this).find('td').eq(3).text();
				var mon = $(this).find('td').eq(4).text();
				var tue = $(this).find('td').eq(5).text();
				var wed = $(this).find('td').eq(6).text();
				var thu = $(this).find('td').eq(7).text();
				var fri = $(this).find('td').eq(8).text();
				var sat = $(this).find('td').eq(9).text();
				var work_hours = $(this).find('td').eq(10).text();
				var cash_advanced = $(this).find('td').eq(12).text();
				var overtime = $(this).find('td').eq(13).text();
				var ut_late = $(this).find('td').eq(14).text();
				var employee_id = $(this).find('td').eq(16).text();
				
				daily_rate = daily_rate.match(pattern).join('');
				cash_advanced = cash_advanced.match(pattern).join('');
				ut_late = ut_late.match(pattern).join('');
				
				postData['dailyRate'] = daily_rate;
				postData['workHours'].push(mon);
				postData['workHours'].push(tue);
				postData['workHours'].push(wed);
				postData['workHours'].push(thu);
				postData['workHours'].push(fri);
				postData['workHours'].push(sat);
				postData['cashAdvanced'] = cash_advanced;
				postData['overtimeHours'] = overtime;
				postData['undertimeLate'] = ut_late;
				postData['employeeEID'] = employee_id;
				
				data['employeesRecords'].push(postData);
			});
			
			options.url = "http://localhost:8080/elena-syspay/ajax/request/post-employee.do";
	        options.dataType = "JSON";
	        options.contentType = "application/json";
	       	options.type = "POST";
	       	options.cache = false;
	        options.data = JSON.stringify(data);
	        options.success = function(data) {
	        	$("#add_success").html(data);
	        };
	        $.ajax(options);
		});
		
	});