package com.linkin.simi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.simi.dao.SimAdDao;
import com.linkin.simi.entity.SimAd;
import com.linkin.simi.model.SearchSimAdDTO;

@Repository
@Transactional
public class SimAdDaoImpl implements SimAdDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void addSimAd(SimAd simAd) {
		entityManager.persist(simAd);
	}

	@Override
	public void updateSimAd(SimAd simAd) {
		entityManager.merge(simAd);
	}

	@Override
	public void deleteSimAd(SimAd simAd) {
		entityManager.remove(simAd);
	}

	@Override
	public SimAd getSimAd(Long id) {
		return entityManager.find(SimAd.class, id);
	}

	@Override
	public List<SimAd> findSimAd(SearchSimAdDTO searchSimAdDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SimAd> criteriaQuery = builder.createQuery(SimAd.class);
		Root<SimAd> root = criteriaQuery.from(SimAd.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchSimAdDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("simNo")),
					"%" + searchSimAdDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}
		
		if (searchSimAdDTO.getStatus() != null) {
			predicates.add(builder.equal(root.get("status"), searchSimAdDTO.getStatus()));
		}

		if (searchSimAdDTO.getTarget() != null) {
			predicates.add(builder.equal(root.get("target"), searchSimAdDTO.getTarget()));
		} 

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<SimAd> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchSimAdDTO.getStart());
		typedQuery.setMaxResults(searchSimAdDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public Long countSimAd(SearchSimAdDTO searchSimAdDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<SimAd> root = criteriaQuery.from(SimAd.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchSimAdDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("simNo")),
					"%" + searchSimAdDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

}
