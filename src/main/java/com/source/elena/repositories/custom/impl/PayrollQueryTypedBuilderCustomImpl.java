package com.source.elena.repositories.custom.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.source.elena.entities.Payroll;
import com.source.elena.repositories.custom.PayrollQueryTypedBuilder;

@Repository
public class PayrollQueryTypedBuilderCustomImpl implements PayrollQueryTypedBuilder {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public List<Payroll> findActiveEmployeesBetweenDates(Date dateFrom, Date dateTo) {
		Class<Payroll> entityClassType = Payroll.class;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Payroll> query = builder.createQuery(entityClassType);
	    Root<Payroll> root = query.from(entityClassType);
	    
	    Predicate whereDateFrom = builder.equal(root.<Date>get("dateFrom"), dateFrom);
	    Predicate whereDateTo = builder.equal(root.<Date>get("dateTo"), dateTo);
	    query.select(root).where(whereDateFrom, whereDateTo);
	    
		TypedQuery<Payroll> typedQuery = entityManager.createQuery(query);
	    return typedQuery.getResultList();
	}
}
