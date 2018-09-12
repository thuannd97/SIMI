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

import com.linkin.simi.dao.CardDao;
import com.linkin.simi.entity.Card;
import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchCardDTO;

@Repository
@Transactional
public class CardDaoImpl implements CardDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public void add(Card card) {
		entityManager.persist(card);
	}

	@Override
	public void detele(Card card) {
		entityManager.remove(card);
	}

	@Override
	public void update(Card card) {
		entityManager.merge(card);
	}

	@Override
	public Card getById(Long id) {
		return entityManager.find(Card.class, id);
	}

	@Override
	public List<Card> find(SearchCardDTO searchCardDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Card> criteriaQuery = builder.createQuery(Card.class);
		Root<Card> root = criteriaQuery.from(Card.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		Join<Card, User> createdBy = root.join("createdBy");
		Join<Card, User> updatedBy = root.join("updatedBy");

		if (searchCardDTO.getCardType() != null) {
			predicates.add(builder.equal(root.get("cardType"), searchCardDTO.getCardType()));
		}
		if (searchCardDTO.getDenomination() != null) {
			predicates.add(builder.equal(root.get("denomination"), searchCardDTO.getDenomination()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchCardDTO.getSortBy().getData(), "id")) {
			if (searchCardDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchCardDTO.getSortBy().getData(), "cardType")) {
			if (searchCardDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("cardType")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("cardType")));
			}
		} else if (StringUtils.equals(searchCardDTO.getSortBy().getData(), "denomination")) {
			if (searchCardDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("denomination")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("denomination")));
			}
		} else if (StringUtils.equals(searchCardDTO.getSortBy().getData(), "quantity")) {
			if (searchCardDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("quantity")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("quantity")));
			}
		} else if (StringUtils.equals(searchCardDTO.getSortBy().getData(), "createdBy")) {
			if (searchCardDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(createdBy.get("createdBy")));
			} else {
				criteriaQuery.orderBy(builder.desc(createdBy.get("createdBy")));
			}
		} else if (StringUtils.equals(searchCardDTO.getSortBy().getData(), "updatedBy")) {
			if (searchCardDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(updatedBy.get("updatedBy")));
			} else {
				criteriaQuery.orderBy(builder.desc(updatedBy.get("updatedBy")));
			}
		} else if (StringUtils.equals(searchCardDTO.getSortBy().getData(), "createdDate")) {
			if (searchCardDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchCardDTO.getSortBy().getData(), "updatedDate")) {
			if (searchCardDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("updatedDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("updatedDate")));
			}
		}

		TypedQuery<Card> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchCardDTO.getStart());
		typedQuery.setMaxResults(searchCardDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchCardDTO searchCardDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Card> root = criteriaQuery.from(Card.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchCardDTO.getCardType() != null) {
			predicates.add(builder.equal(root.get("cardType"), searchCardDTO.getCardType()));
		}
		if (searchCardDTO.getDenomination() != null) {
			predicates.add(builder.equal(root.get("denomination"), searchCardDTO.getDenomination()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

}
