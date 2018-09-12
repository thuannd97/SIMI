package com.linkin.simi.service;

import java.util.List;

import com.linkin.simi.model.CommentDTO;
import com.linkin.simi.model.SearchCommentDTO;

public interface CommentService {

	public void addComment(CommentDTO commentDTO);

	public void updateComment(CommentDTO commentDTO);

	public void deleteComment(Long id);

	public CommentDTO getCommentById(Long id);

	public List<CommentDTO> findComment(SearchCommentDTO searchCommentDTO);

	public Long countComment(SearchCommentDTO searchCommentDTO);
}
