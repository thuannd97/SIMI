package com.linkin.simi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

import com.linkin.simi.dao.SimInfoInstallmentDao;
import com.linkin.simi.entity.InfoInstallment;
import com.linkin.simi.entity.Installment;
import com.linkin.simi.entity.Sim;
import com.linkin.simi.entity.SimBuy;
import com.linkin.simi.model.SearchInfoInstallmentDTO;

@Repository
@Transactional
public class SimInfoInstallmentDaoImpl implements SimInfoInstallmentDao{

	@Autowired
	EntityManager entityManager;
	
	@Override
	public void add(InfoInstallment infoInstallment) {
		entityManager.persist(infoInstallment);
	}

	@Override
	public void detele(InfoInstallment infoInstallment) {
		entityManager.remove(infoInstallment);
		
	}

	@Override
	public void update(InfoInstallment infoInstallment) {
		entityManager.merge(infoInstallment);
		
	}

	@Override
	public InfoInstallment getById(int id) {
		return entityManager.find(InfoInstallment.class, id);
	}

	@Override
	public List<InfoInstallment> find(SearchInfoInstallmentDTO searchInfoInstallmentDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InfoInstallment> criteriaQuery = builder.createQuery(InfoInstallment.class);
		Root<InfoInstallment> root = criteriaQuery.from(InfoInstallment.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		

		if (StringUtils.isNotBlank(searchInfoInstallmentDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("id")),
					"%" + searchInfoInstallmentDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}
		
		if(searchInfoInstallmentDTO.getId() != null) {
			predicates.add(builder.equal(root.get("id"), searchInfoInstallmentDTO.getId()));
		}
		
		
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchInfoInstallmentDTO.getSortBy().getData(), "id")) {
			if (searchInfoInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchInfoInstallmentDTO.getSortBy().getData(), "simNo")) {
			if (searchInfoInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("simNo")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("simNo")));
			}
		} else if (StringUtils.equals(searchInfoInstallmentDTO.getSortBy().getData(), "customerName")) {
			if (searchInfoInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("customerName")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("customerName")));
			}
		} else if (StringUtils.equals(searchInfoInstallmentDTO.getSortBy().getData(), "customerPhone")) {
			if (searchInfoInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("customerPhone")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("customerPhone")));
			}
		} else if (StringUtils.equals(searchInfoInstallmentDTO.getSortBy().getData(), "interest")) {
			if (searchInfoInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("interest")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("interest")));
			}
		}
		else if (StringUtils.equals(searchInfoInstallmentDTO.getSortBy().getData(), "interest")) {
			if (searchInfoInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("interest")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("interest")));
			}
		}
		else if (StringUtils.equals(searchInfoInstallmentDTO.getSortBy().getData(), "interest")) {
			if (searchInfoInstallmentDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("interest")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("interest")));
			}
		}

		TypedQuery<InfoInstallment> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchInfoInstallmentDTO.getStart());
		typedQuery.setMaxResults(searchInfoInstallmentDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchInfoInstallmentDTO searchInfoInstallmentDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<InfoInstallment> root = criteriaQuery.from(InfoInstallment.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchInfoInstallmentDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("times")),
					"%" + searchInfoInstallmentDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}


	@Override
	public InfoInstallment getByIdInfostallment(Long idInstallment) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InfoInstallment> criteriaQuery = builder.createQuery(InfoInstallment.class);
		Root<InfoInstallment> root = criteriaQuery.from(InfoInstallment.class);
		Join<InfoInstallment, Installment> installment = root.join("installment");

		criteriaQuery.where(builder.equal(builder.lower(installment.get("id")), idInstallment));
		try {
			TypedQuery<InfoInstallment> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
