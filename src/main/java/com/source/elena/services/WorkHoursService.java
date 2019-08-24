package com.source.elena.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.source.elena.entities.WorkHoursDate;
import com.source.elena.repositories.WorkHoursRepository;

@Service
public class WorkHoursService {

	@Autowired
	private WorkHoursRepository workHoursRepository;
	
	public Optional<WorkHoursDate> save(WorkHoursDate workHours) {
		return Optional.ofNullable(workHoursRepository.save(workHours));
	}
}
