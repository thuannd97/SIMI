package com.linkin.simi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.linkin.simi.dao.UserDao;
import com.linkin.simi.entity.Role;
import com.linkin.simi.entity.User;
import com.linkin.simi.model.SearchUserDTO;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(User User) {
		entityManager.persist(User);
	}

	@Override
	public void update(User User) {
		entityManager.merge(User);
	}

	@Override
	public void delete(User User) {
		entityManager.remove(User);
	}

	@Override
	public List<User> find(SearchUserDTO searchUserDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);

		Join<User, Role> role = root.join("role");
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchUserDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("phone")),
					"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
			Predicate predicate3 = builder.like(builder.lower(root.get("name")),
					"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
			predicates.add(builder.or(predicate2, predicate3));
		}

		if (searchUserDTO.getRoleId() != null) {
			predicates.add(builder.equal(role.get("id"), searchUserDTO.getRoleId()));
		}

		if (searchUserDTO.getEnabled() != null) {
			predicates.add(builder.equal(root.get("enabled"), searchUserDTO.getEnabled()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "id")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "phone")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("phone")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("phone")));
			}
		} else if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "name")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("name")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("name")));
			}
		} else if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "createdDate")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("createdDate")));
			}
		} else if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "enabled")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(builder.asc(root.get("enabled")));
			} else {
				criteriaQuery.orderBy(builder.desc(root.get("enabled")));
			}
		}

		TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchUserDTO.getStart());
		typedQuery.setMaxResults(searchUserDTO.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchUserDTO searchUserDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from(User.class);
		Join<User, Role> role = root.join("role");
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchUserDTO.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("phone")),
					"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
			Predicate predicate3 = builder.like(builder.lower(root.get("name")),
					"%" + searchUserDTO.getKeyword().toLowerCase() + "%");
			predicates.add(builder.or(predicate2, predicate3));
		}

		if (searchUserDTO.getRoleId() != null) {
			predicates.add(builder.equal(role.get("id"), searchUserDTO.getRoleId()));
		}

		if (searchUserDTO.getEnabled() != null) {
			predicates.add(builder.equal(root.get("enabled"), searchUserDTO.getEnabled()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public User getById(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User getByPhone(String phone) {
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
			Root<User> root = criteriaQuery.from(User.class);

			criteriaQuery.where(builder.equal(builder.lower(root.get("phone")), phone));

			TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery.select(root).distinct(true));
			return typedQuery.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}

}
