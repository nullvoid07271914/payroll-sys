package com.source.elena.repositories;

import org.springframework.stereotype.Repository;

import com.source.elena.entities.WorkHoursDate;
import com.source.elena.repositories.custom.BaseRepository;

@Repository
public interface WorkHoursRepository extends BaseRepository<WorkHoursDate, String> {

}
