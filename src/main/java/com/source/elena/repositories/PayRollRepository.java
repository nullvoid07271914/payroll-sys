package com.source.elena.repositories;

import org.springframework.stereotype.Repository;

import com.source.elena.entities.Payroll;
import com.source.elena.repositories.custom.BaseRepository;
import com.source.elena.repositories.custom.PayrollQueryTypedBuilder;

@Repository
public interface PayRollRepository extends BaseRepository<Payroll, String>, PayrollQueryTypedBuilder {

}
