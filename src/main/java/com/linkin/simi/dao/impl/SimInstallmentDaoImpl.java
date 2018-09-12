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

import com.linkin.simi.dao.SimInstallmentDao;
import com.linkin.simi.entity.Installment;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.model.SearchInstallmentDTO;
import com.linkin.simi.model.SearchSimDTO;

@Repository
@Transactional
public class SimInstallmentDaoImpl implements SimInstallmentDao {

	@Autowired
	EntityManager entityManager;

	@Override
	public void add(Installment installment) {
		entityManager.persist(installment);
	}

	@Override
	public void detele(Installment installment) {
		entityManager.remove(installment);
	}

	@Override
	public void update(Installment installment) {
		entityManager.merge(installment);
	}

	@Override
	public List<Installment> find(SearchInstallmentDTO searchInstallmentDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Installment> criteriaQuery = builder.createQuery(Installment.class);
		Root<Installment> root = criteriaQuery.from(Installment.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchInstallmentDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("simNo")),
					"%" + searchInstallmentDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchInstallmentDTO.getSortBy().getData(), "id")) {
			if (searchInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchInstallmentDTO.getSortBy().getData(), "simNo")) {
			if (searchInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("simNo")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("simNo")));
			}
		} else if (StringUtils.equals(searchInstallmentDTO.getSortBy().getData(), "customerName")) {
			if (searchInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("customerName")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("customerName")));
			}
		} else if (StringUtils.equals(searchInstallmentDTO.getSortBy().getData(), "customerPhone")) {
			if (searchInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("customerPhone")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("customerPhone")));
			}
		} else if (StringUtils.equals(searchInstallmentDTO.getSortBy().getData(), "interest")) {
			if (searchInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("interest")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("interest")));
			}
		} else if (StringUtils.equals(searchInstallmentDTO.getSortBy().getData(), "interest")) {
			if (searchInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("interest")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("interest")));
			}
		} else if (StringUtils.equals(searchInstallmentDTO.getSortBy().getData(), "interest")) {
			if (searchInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("interest")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("interest")));
			}
		}

		TypedQuery<Installment> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchInstallmentDTO.getStart());
		typedQuery.setMaxResults(searchInstallmentDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchInstallmentDTO searchInstallmentSimDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Installment> root = criteriaQuery.from(Installment.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchInstallmentSimDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("simNo")),
					"%" + searchInstallmentSimDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Installment getById(Long id) {
		try {
			return entityManager.find(Installment.class, id);

		} catch (Exception e) {
			return null;
		}

	}


}
