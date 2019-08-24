package com.source.elena.repositories.custom.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.source.elena.repositories.custom.BaseRepository;

public class ExtendedRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
	
	private JpaEntityInformation<T, ?> entityInformation;

	private EntityManager entityManager;

	public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}
	
	@Transactional
	public List<T> findActiveEmployees() {
		Class<T> entityClassType = entityInformation.getJavaType();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> query = builder.createQuery(entityClassType);
	    Root<T> root = query.from(entityClassType);
	    
	    Predicate whereActiveEmployees = builder.equal(root.<String>get("active"), 1);
	    query.select(root).where(whereActiveEmployees);
	    TypedQuery<T> typedQuery = entityManager.createQuery(query);
	    return typedQuery.getResultList();
	}
	
	@Transactional
	public List<T> findActiveEmployeesBetweenDates(Date dateFrom, Date dateTo) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<T> query = builder.createQuery(getDomainClass());
	    Root<T> root = query.from(getDomainClass());
	    
	    Predicate whereDateFrom = builder.equal(root.<Date>get("dateFrom"), dateFrom);
	    Predicate whereDateTo = builder.equal(root.<Date>get("dateTo"), dateTo);
	    query.select(root).where(whereDateFrom, whereDateTo);
	    
		TypedQuery<T> typedQuery = entityManager.createQuery(query);
	    return typedQuery.getResultList();
	}
}
