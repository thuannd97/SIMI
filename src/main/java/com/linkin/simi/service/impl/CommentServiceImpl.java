package com.linkin.simi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.simi.dao.CommentDAO;
import com.linkin.simi.entity.Comment;
import com.linkin.simi.model.CommentDTO;
import com.linkin.simi.model.SearchCommentDTO;
import com.linkin.simi.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO commentDAO;

	@Override
	public void addComment(CommentDTO commentDTO) {
		Comment comment = new Comment();
		comment.setContent(commentDTO.getContent());
		comment.setName(commentDTO.getName());
		comment.setPhone(commentDTO.getPhone());

		commentDAO.addComment(comment);
		commentDTO.setId(comment.getId());
	}

	@Override
	public void updateComment(CommentDTO commentDTO) {
		Comment comment = commentDAO.getCommentByid(commentDTO.getId());
		if (comment != null) {
			comment.setId(commentDTO.getId());
			comment.setContent(commentDTO.getContent());
			comment.setName(commentDTO.getName());
			comment.setPhone(commentDTO.getPhone());

			commentDAO.updateComment(comment);
		}
	}

	@Override
	public void deleteComment(Long id) {
		Comment comment = commentDAO.getCommentByid(id);
		if (comment != null) {
			commentDAO.deleteComment(comment);
		}
	}

	@Override
	public CommentDTO getCommentById(Long id) {
		Comment comment = commentDAO.getCommentByid(id);
		if (comment != null) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setId(comment.getId());
			commentDTO.setContent(comment.getContent());
			commentDTO.setName(comment.getName());
			commentDTO.setPhone(comment.getPhone());

			return commentDTO;
		}
		return null;
	}

	@Override
	public List<CommentDTO> findComment(SearchCommentDTO searchCommentDTO) {
		List<Comment> comments = commentDAO.findComment(searchCommentDTO);
		List<CommentDTO> commentDTOs = new ArrayList<CommentDTO>();
		comments.forEach(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setId(comment.getId());
			commentDTO.setContent(comment.getContent());
			commentDTO.setName(comment.getName());
			commentDTO.setPhone(comment.getPhone());

			commentDTOs.add(commentDTO);
		});
		return commentDTOs;
	}

	@Override
	public Long countComment(SearchCommentDTO searchCommentDTO) {
		return commentDAO.coutComment(searchCommentDTO);
	}

}
