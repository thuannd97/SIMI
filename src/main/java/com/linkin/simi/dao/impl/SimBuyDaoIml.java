package com.linkin.simi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.simi.dao.SimBuyDao;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.entity.SimBuy;
import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchSimBuyDTO;
import com.linkin.simi.utils.DateTimeUtils;

@Repository
@Transactional
public class SimBuyDaoIml implements SimBuyDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public void addSimBuy(SimBuy sim) {
		entityManager.persist(sim);
	}

	@Override
	public void deteleSimBuy(SimBuy sim) {
		entityManager.remove(sim);
	}

	@Override
	public void updateSimBuy(SimBuy sim) {
		entityManager.merge(sim);
	}

	@Override
	public List<SimBuy> find(SearchSimBuyDTO searchSimBuyDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SimBuy> criteriaQuery = builder.createQuery(SimBuy.class);
		Root<SimBuy> root = criteriaQuery.from(SimBuy.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<SimBuy, Sim> sim = root.join("sim");
		Join<SimBuy, User> buyer = root.join("buyer");
		Join<SimBuy, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchSimBuyDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(sim.get("simNo")),
					"%" + searchSimBuyDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getBuyerName())) {
			Predicate predicate = builder.like(builder.lower(buyer.get("name")),
					"%" + searchSimBuyDTO.getBuyerName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getBuyerName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")),
					"%" + searchSimBuyDTO.getCreatedByName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getFromDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("buyDate"),
						DateTimeUtils.parseDate(searchSimBuyDTO.getFromDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getToDate())) {
			try {
				Predicate predicate = builder.lessThanOrEqualTo(root.get("buyDate"),
						DateTimeUtils.parseDate(searchSimBuyDTO.getToDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchSimBuyDTO.getSortBy().getData(), "id")) {
			if (searchSimBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchSimBuyDTO.getSortBy().getData(), "simNo")) {
			if (searchSimBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(sim.get("simNo")));
			} else {
				criteriaQuery.orderBy(builder.desc(sim.get("simNo")));
			}
		} else if (StringUtils.equals(searchSimBuyDTO.getSortBy().getData(), "buyDate")) {
			if (searchSimBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("buyDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("buyDate")));
			}
		} else if (StringUtils.equals(searchSimBuyDTO.getSortBy().getData(), "buyerName")) {
			if (searchSimBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(buyer.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(buyer.get("name")));
			}
		} else if (StringUtils.equals(searchSimBuyDTO.getSortBy().getData(), "cost")) {
			if (searchSimBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("cost")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("cost")));
			}
		} else if (StringUtils.equals(searchSimBuyDTO.getSortBy().getData(), "createdBy")) {
			if (searchSimBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(createdBy.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(createdBy.get("name")));
			}
		} else if (StringUtils.equals(searchSimBuyDTO.getSortBy().getData(), "createdDate")) {
			if (searchSimBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchSimBuyDTO.getSortBy().getData(), "updatedDate")) {
			if (searchSimBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("updatedDate")));
			}
		}

		TypedQuery<SimBuy> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchSimBuyDTO.getStart());
		typedQuery.setMaxResults(searchSimBuyDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchSimBuyDTO searchSimBuyDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<SimBuy> root = criteriaQuery.from(SimBuy.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<SimBuy, Sim> sim = root.join("sim");
		Join<SimBuy, User> buyer = root.join("buyer");
		Join<SimBuy, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchSimBuyDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(sim.get("simNo")),
					"%" + searchSimBuyDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getBuyerName())) {
			Predicate predicate = builder.like(builder.lower(buyer.get("name")),
					"%" + searchSimBuyDTO.getBuyerName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getBuyerName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")),
					"%" + searchSimBuyDTO.getCreatedByName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getFromDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("buyDate"),
						DateTimeUtils.parseDate(searchSimBuyDTO.getFromDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getToDate())) {
			try {
				Predicate predicate = builder.lessThanOrEqualTo(root.get("buyDate"),
						DateTimeUtils.parseDate(searchSimBuyDTO.getToDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public SimBuy getSimBuyById(Long id) {
		return entityManager.find(SimBuy.class, id);
	}

	@Override
	public Long sum(SearchSimBuyDTO searchSimBuyDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<SimBuy> root = criteriaQuery.from(SimBuy.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<SimBuy, Sim> sim = root.join("sim");
		Join<SimBuy, User> buyer = root.join("buyer");
		Join<SimBuy, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchSimBuyDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(sim.get("simNo")),
					"%" + searchSimBuyDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getBuyerName())) {
			Predicate predicate = builder.like(builder.lower(buyer.get("name")),
					"%" + searchSimBuyDTO.getBuyerName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getBuyerName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")),
					"%" + searchSimBuyDTO.getCreatedByName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getFromDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("buyDate"),
						DateTimeUtils.parseDate(searchSimBuyDTO.getFromDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSimBuyDTO.getToDate())) {
			try {
				Predicate predicate = builder.lessThanOrEqualTo(root.get("buyDate"),
						DateTimeUtils.parseDate(searchSimBuyDTO.getToDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.sum(root.get("cost"))));
		return typedQuery.getSingleResult();
	}

}
