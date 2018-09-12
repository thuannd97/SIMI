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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.simi.dao.SimDao;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.model.SearchSimDTO;

@Repository
@Transactional
public class SimDaoIml implements SimDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public void add(Sim sim) {
		entityManager.persist(sim);
	}

	@Override
	public void detele(Sim sim) {
		entityManager.remove(sim);
	}

	@Override
	public void update(Sim sim) {
		entityManager.merge(sim);
	}

	@Override
	public Sim getById(Long id) {
		return entityManager.find(Sim.class, id);
	}

	@Override
	public List<Sim> find(SearchSimDTO searchSimDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sim> criteriaQuery = builder.createQuery(Sim.class);
		Root<Sim> root = criteriaQuery.from(Sim.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchSimDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("simNo")),
					"%" + searchSimDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}
		if (searchSimDTO.getStatus() != null) {
			predicates.add(builder.equal(root.get("status"), searchSimDTO.getStatus()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchSimDTO.getSortBy().getData(), "id")) {
			if (searchSimDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchSimDTO.getSortBy().getData(), "simNo")) {
			if (searchSimDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("simNo")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("simNo")));
			}
		} else if (StringUtils.equals(searchSimDTO.getSortBy().getData(), "price")) {
			if (searchSimDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("price")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("price")));
			}
		} else if (StringUtils.equals(searchSimDTO.getSortBy().getData(), "createdDate")) {
			if (searchSimDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchSimDTO.getSortBy().getData(), "updatedDate")) {
			if (searchSimDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("updatedDate")));
			}
		}

		TypedQuery<Sim> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchSimDTO.getStart());
		typedQuery.setMaxResults(searchSimDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchSimDTO searchSimDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Sim> root = criteriaQuery.from(Sim.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchSimDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("simNo")),
					"%" + searchSimDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Sim getByPhone(String phone) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sim> criteriaQuery = builder.createQuery(Sim.class);
		Root<Sim> root = criteriaQuery.from(Sim.class);

		criteriaQuery.where(builder.equal(builder.lower(root.get("simNo")), phone));
		try {
			TypedQuery<Sim> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

}
