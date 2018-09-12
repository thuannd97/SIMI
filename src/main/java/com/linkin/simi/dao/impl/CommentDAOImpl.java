package com.linkin.simi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.linkin.simi.dao.CommentDAO;
import com.linkin.simi.entity.Comment;
import com.linkin.simi.model.SearchCommentDTO;

@Repository
@Transactional
public class CommentDAOImpl implements CommentDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void addComment(Comment comment) {
		entityManager.persist(comment);
	}

	@Override
	public void updateComment(Comment comment) {
		entityManager.merge(comment);
	}

	@Override
	public void deleteComment(Comment comment) {
		entityManager.remove(comment);
	}

	@Override
	public List<Comment> findComment(SearchCommentDTO searchComment) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Comment> criteriaQuery = builder.createQuery(Comment.class);
		Root<Comment> root = criteriaQuery.from(Comment.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchComment.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("name")),
					"%" + searchComment.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		if (StringUtils.isNotBlank(searchComment.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("phone")),
					"%" + searchComment.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Comment> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		typedQuery.setFirstResult(searchComment.getStart());
		typedQuery.setMaxResults(searchComment.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public Comment getCommentByid(Long id) {
		return entityManager.find(Comment.class, id);
	}

	@Override
	public Long coutComment(SearchCommentDTO searchComment) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Comment> root = criteriaQuery.from(Comment.class);

		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchComment.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("name")),
					"%" + searchComment.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		if (StringUtils.isNotBlank(searchComment.getKeyword())) {
			Predicate predicate2 = builder.like(builder.lower(root.get("phone")),
					"%" + searchComment.getKeyword().toLowerCase() + "%");
			predicates.add(predicate2);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		return entityManager.createQuery(criteriaQuery.select(builder.count(root))).getSingleResult();
	}

}
