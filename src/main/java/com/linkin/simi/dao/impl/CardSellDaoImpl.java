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

import com.linkin.simi.dao.CardSellDao;
import com.linkin.simi.entity.CardSell;
import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchCardSellDTO;
import com.linkin.simi.utils.DateTimeUtils;

@Repository
@Transactional
public class CardSellDaoImpl implements CardSellDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public void addCardSell(CardSell cardSell) {
		entityManager.persist(cardSell);
	}

	@Override
	public void deteleCardSell(CardSell cardSell) {
		entityManager.remove(cardSell);
	}

	@Override
	public void updateCardSell(CardSell cardSell) {
		entityManager.merge(cardSell);
	}

	@Override
	public CardSell getCardSellById(Long id) {
		return entityManager.find(CardSell.class, id);
	}

	@Override
	public long count(SearchCardSellDTO searchCardSellDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<CardSell> root = criteriaQuery.from(CardSell.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<CardSell, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchCardSellDTO.getCreatedByName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")), "%" + searchCardSellDTO.getCreatedByName().toLowerCase() + "%");
			predicates.add(predicate);
		}
		if (StringUtils.isNotBlank(searchCardSellDTO.getSellDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("sellDate"), DateTimeUtils.parseDate(searchCardSellDTO.getSellDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}
		if (searchCardSellDTO.getCardType() != null) {
			predicates.add(builder.equal(root.get("cardType"), searchCardSellDTO.getCardType()));
		}
		if (searchCardSellDTO.getDenomination() != null) {
			predicates.add(builder.equal(root.get("denomination"), searchCardSellDTO.getDenomination()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public List<CardSell> find(SearchCardSellDTO searchCardSellDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CardSell> criteriaQuery = builder.createQuery(CardSell.class);
		Root<CardSell> root = criteriaQuery.from(CardSell.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<CardSell, User> updatedBy = root.join("updatedBy");
		Join<CardSell, User> createdBy = root.join("createdBy");

		if (StringUtils.isNotBlank(searchCardSellDTO.getCreatedByName())) {
			Predicate predicate = builder.like(builder.lower(createdBy.get("name")), "%" + searchCardSellDTO.getCreatedByName().toLowerCase() + "%");
			predicates.add(predicate);
		}
		if (StringUtils.isNotBlank(searchCardSellDTO.getSellDate())) {
			try {
				Predicate predicate = builder.greaterThanOrEqualTo(root.get("sellDate"), DateTimeUtils.parseDate(searchCardSellDTO.getSellDate(), DateTimeUtils.DD_MM_YYYY));
				predicates.add(predicate);
			} catch (RuntimeException exception) {
				//
			}
		}
		if (searchCardSellDTO.getCardType() != null) {
			predicates.add(builder.equal(root.get("cardType"), searchCardSellDTO.getCardType()));
		}
		if (searchCardSellDTO.getDenomination() != null) {
			predicates.add(builder.equal(root.get("denomination"), searchCardSellDTO.getDenomination()));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "id")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "denomination")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("denomination")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("denomination")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "quantity")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("quantity")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("quantity")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "cardType")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("cardType")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("cardType")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "unitPrice")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("unitPrice")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("unitPrice")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "sellDate")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("sellDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("sellDate")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "createdBy")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(createdBy.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(createdBy.get("name")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "updatedBy")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(updatedBy.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(updatedBy.get("name")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "createdDate")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchCardSellDTO.getSortBy().getData(), "updatedDate")) {
			if (searchCardSellDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("updatedDate")));
			}
		}

		TypedQuery<CardSell> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchCardSellDTO.getStart());
		typedQuery.setMaxResults(searchCardSellDTO.getPageSize());
		return typedQuery.getResultList();
	}

}
