package com.linkin.simi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.simi.dao.SimPayTypeDao;
import com.linkin.simi.entity.PayType;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.model.SearchPayTypeDTO;

@Repository
@Transactional
public class SimPayTypeDaoImpl implements SimPayTypeDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public void add(PayType payType) {
		entityManager.persist(payType);
	}

	@Override
	public void detele(PayType payType) {
		entityManager.remove(payType);
	}

	@Override
	public void update(PayType payType) {
		entityManager.merge(payType);
	}


	@Override
	public List<PayType> find(SearchPayTypeDTO searchPayTypeDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PayType> criteriaQuery = builder.createQuery(PayType.class);
		Root<PayType> root = criteriaQuery.from(PayType.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchPayTypeDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("id")),
					"%" + searchPayTypeDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchPayTypeDTO.getSortBy().getData(), "id")) {
			if (searchPayTypeDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchPayTypeDTO.getSortBy().getData(), "type")) {
			if (searchPayTypeDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("type;")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("type")));
			}
		}

		TypedQuery<PayType> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchPayTypeDTO.getStart());
		typedQuery.setMaxResults(searchPayTypeDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchPayTypeDTO searchPayTypeDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<PayType> root = criteriaQuery.from(PayType.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchPayTypeDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("id")),
					"%" + searchPayTypeDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}
	
	@Override
	public PayType getById(int id) { 
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PayType> criteriaQuery = builder.createQuery(PayType.class);
		Root<PayType> root = criteriaQuery.from(PayType.class);

		criteriaQuery.where(builder.equal(builder.lower(root.get("id")), id));
		try {
			TypedQuery<PayType> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

}
