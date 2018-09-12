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

import com.linkin.simi.dao.SimSellDao;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.entity.SimSell;
import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchSimSellDTO;
import com.linkin.simi.utils.DateTimeUtils;

@Repository
@Transactional
public class SimSellDaoIml implements SimSellDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public void addSimSell(SimSell sim) {
		entityManager.persist(sim);
	}

	@Override
	public void updateSimSell(SimSell sim) {
		entityManager.merge(sim);
	}

	@Override
	public List<SimSell> find(SearchSimSellDTO searchSimSellDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SimSell> criteriaQuery = builder.createQuery(SimSell.class);
		Root<SimSell> root = criteriaQuery.from(SimSell.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<SimSell, Sim> sim = root.join("sim");
		Join<SimSell, User> seller = root.join("seller");
		Join<SimSell, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchSimSellDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(sim.get("simNo")),
					"%" + searchSimSellDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getSellerName())) {
			Predicate predicate = builder.like(builder.lower(seller.get("name")),
					"%" + searchSimSellDTO.getSellerName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getCreatedName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")),
					"%" + searchSimSellDTO.getCreatedName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getFromDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("sellDate"),
						DateTimeUtils.parseDate(searchSimSellDTO.getFromDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getToDate())) {
			try {
				Predicate predicate = builder.lessThanOrEqualTo(root.get("sellDate"),
						DateTimeUtils.parseDate(searchSimSellDTO.getToDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "id")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "simNo")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(sim.get("simNo")));
			} else {
				criteriaQuery.orderBy(builder.desc(sim.get("simNo")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "sellPrice")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("sellPrice")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("sellPrice")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "deposit")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("deposit")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("deposit")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "customerName")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("customerName")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("customerName")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "customerInfo")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("customerInfo")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("customerInfo")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "sellDate")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("sellDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("sellDate")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "sellerName")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(seller.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(seller.get("name")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "createdBy")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(createdBy.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(createdBy.get("name")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "createdDate")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchSimSellDTO.getSortBy().getData(), "updatedDate")) {
			if (searchSimSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("updatedDate")));
			}
		}

		TypedQuery<SimSell> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchSimSellDTO.getStart());
		typedQuery.setMaxResults(searchSimSellDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchSimSellDTO searchSimSellDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<SimSell> root = criteriaQuery.from(SimSell.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<SimSell, Sim> sim = root.join("sim");
		Join<SimSell, User> seller = root.join("seller");
		Join<SimSell, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchSimSellDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(sim.get("simNo")),
					"%" + searchSimSellDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getSellerName())) {
			Predicate predicate = builder.like(builder.lower(seller.get("name")),
					"%" + searchSimSellDTO.getSellerName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getCreatedName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")),
					"%" + searchSimSellDTO.getCreatedName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getFromDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("sellDate"),
						DateTimeUtils.parseDate(searchSimSellDTO.getFromDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getToDate())) {
			try {
				Predicate predicate = builder.lessThanOrEqualTo(root.get("sellDate"),
						DateTimeUtils.parseDate(searchSimSellDTO.getToDate(), DateTimeUtils.DD_MM_YYYY));
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
	public void deteleSimSell(SimSell sim) {
		entityManager.remove(sim);
	}

	@Override
	public Long sum(SearchSimSellDTO searchSimSellDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<SimSell> root = criteriaQuery.from(SimSell.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<SimSell, Sim> sim = root.join("sim");
		Join<SimSell, User> seller = root.join("seller");
		Join<SimSell, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchSimSellDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(sim.get("simNo")),
					"%" + searchSimSellDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getSellerName())) {
			Predicate predicate = builder.like(builder.lower(seller.get("name")),
					"%" + searchSimSellDTO.getSellerName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getCreatedName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")),
					"%" + searchSimSellDTO.getCreatedName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getFromDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("sellDate"),
						DateTimeUtils.parseDate(searchSimSellDTO.getFromDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getToDate())) {
			try {
				Predicate predicate = builder.lessThanOrEqualTo(root.get("sellDate"),
						DateTimeUtils.parseDate(searchSimSellDTO.getToDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager
				.createQuery(criteriaQuery.select(builder.sum(root.get("sellPrice"))));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long sumDeposit(SearchSimSellDTO searchSimSellDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<SimSell> root = criteriaQuery.from(SimSell.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<SimSell, Sim> sim = root.join("sim");
		Join<SimSell, User> seller = root.join("seller");
		Join<SimSell, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchSimSellDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(sim.get("simNo")),
					"%" + searchSimSellDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getSellerName())) {
			Predicate predicate = builder.like(builder.lower(seller.get("name")),
					"%" + searchSimSellDTO.getSellerName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getCreatedName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")),
					"%" + searchSimSellDTO.getCreatedName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getFromDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("sellDate"),
						DateTimeUtils.parseDate(searchSimSellDTO.getFromDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		if (StringUtils.isNotBlank(searchSimSellDTO.getToDate())) {
			try {
				Predicate predicate = builder.lessThanOrEqualTo(root.get("sellDate"),
						DateTimeUtils.parseDate(searchSimSellDTO.getToDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.sum(root.get("deposit"))));
		return typedQuery.getSingleResult();
	}

	@Override
	public SimSell getSimSaleById(Long id) {
		return entityManager.find(SimSell.class, id);
	}

}
