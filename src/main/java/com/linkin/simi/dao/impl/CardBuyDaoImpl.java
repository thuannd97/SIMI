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
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.simi.dao.CardBuyDao;
import com.linkin.simi.entity.CardBuy;
import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchCardBuyDTO;
import com.linkin.simi.utils.DateTimeUtils;

@Repository
@Transactional
public class CardBuyDaoImpl implements CardBuyDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<CardBuy> find(SearchCardBuyDTO searchCardBuyDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CardBuy> criteriaQuery = builder.createQuery(CardBuy.class);
		Root<CardBuy> root = criteriaQuery.from(CardBuy.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<CardBuy, User> updatedBy = root.join("updatedBy");
		Join<CardBuy, User> createdBy = root.join("createdBy");

		if (searchCardBuyDTO.getCardType() != null) {
			predicates.add(builder.equal(root.get("cardType"), searchCardBuyDTO.getCardType()));
		}

		if (StringUtils.isNotBlank(searchCardBuyDTO.getCreatedByName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")), "%" + searchCardBuyDTO.getCreatedByName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchCardBuyDTO.getBuyDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("buyDate"), DateTimeUtils.parseDate(searchCardBuyDTO.getBuyDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "id")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "quantity")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("quantity")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("quantity")));
			}
		} else if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "cardType")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("cardType")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("cardType")));
			}
		} else if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "buyDate")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("buyDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("buyDate")));
			}
		} else if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "unitPrice")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("unitPrice")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("unitPrice")));
			}
		} else if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "updatedBy")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(updatedBy.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(updatedBy.get("name")));
			}
		} else if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "createdBy")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(createdBy.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(createdBy.get("name")));
			}
		} else if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "createdDate")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchCardBuyDTO.getSortBy().getData(), "updatedDate")) {
			if (searchCardBuyDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("updatedDate")));
			}
		}

		TypedQuery<CardBuy> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchCardBuyDTO.getStart());
		typedQuery.setMaxResults(searchCardBuyDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchCardBuyDTO searchCardBuyDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<CardBuy> root = criteriaQuery.from(CardBuy.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<CardBuy, User> createdBy = root.join("createdBy");

		if (searchCardBuyDTO.getCardType() != null) {
			predicates.add(builder.equal(root.get("cardType"), searchCardBuyDTO.getCardType()));
		}

		if (StringUtils.isNotBlank(searchCardBuyDTO.getCreatedByName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")), "%" + searchCardBuyDTO.getCreatedByName().toLowerCase() + "%");
			predicates.add(predicate);
		}

		if (StringUtils.isNotBlank(searchCardBuyDTO.getBuyDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("buyDate"), DateTimeUtils.parseDate(searchCardBuyDTO.getBuyDate(), DateTimeUtils.DD_MM_YYYY));
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
	public void addCardBuy(CardBuy cardBuy) {
		entityManager.persist(cardBuy);
	}

	@Override
	public void deteleCardBuy(CardBuy cardBuy) {
		entityManager.remove(cardBuy);
	}

	@Override
	public void updateCardBuy(CardBuy cardBuy) {
		entityManager.merge(cardBuy);
	}

	@Override
	public CardBuy getCardBuyById(Long id) {
		return entityManager.find(CardBuy.class, id);
	}

}
