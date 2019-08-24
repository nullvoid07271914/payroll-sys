package com.source.elena.repositories.custom.impl;

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

import com.source.elena.entities.Employee;
import com.source.elena.repositories.custom.EmployeeQueryTypedBuilder;

@Repository
public class EmployeeQueryTypedBuilderCustomImpl implements EmployeeQueryTypedBuilder {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public List<Employee> findActiveEmployees() {
		Class<Employee> entityClassType = Employee.class;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Employee> query = builder.createQuery(entityClassType);
	    Root<Employee> root = query.from(entityClassType);
	    
	    Predicate whereActiveEmployees = builder.equal(root.<String>get("active"), 1);
	    query.select(root).where(whereActiveEmployees);
	    TypedQuery<Employee> typedQuery = entityManager.createQuery(query);
	    return typedQuery.getResultList();
	}
}
