package com.linkin.simi.dao;

import java.util.List;

import com.linkin.simi.entity.Comment;
import com.linkin.simi.model.SearchCommentDTO;

public interface CommentDAO {

	public void addComment(Comment comment);

	public void updateComment(Comment comment);

	public void deleteComment(Comment comment);

	public List<Comment> findComment(SearchCommentDTO searchComment);

	public Comment getCommentByid(Long id);

	public Long coutComment(SearchCommentDTO searchCommentDTO);
}
