package com.linkin.simi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linkin.simi.model.CommentDTO;
import com.linkin.simi.model.ResponseDTO;
import com.linkin.simi.model.SearchCommentDTO;
import com.linkin.simi.service.CommentService;
import com.linkin.simi.utils.URLConstant;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	// @GetMapping("/simad/add")
	// public String addComment(Model model) {
	// model.addAttribute("commentDTO", new CommentDTO());
	// return "admin/comment/add-comment";
	// }
	//
	// @PostMapping("/comment/add")
	// public String addComment(@ModelAttribute("commentDTO") CommentDTO commentDTO)
	// {
	// commentService.addComment(commentDTO);
	// return "redirect:/comments";
	// }

	@PostMapping(URLConstant.STAFF + "/comment/update")
	public String updateComment(@ModelAttribute("commentDTO") CommentDTO commentDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "admin/simad/update-simad";
		}
		try {
			commentService.updateComment(commentDTO);
		} catch (DataIntegrityViolationException ex) {
			return "admin/comment/update-comment";
		}
		return "redirect:/comments";
	}

	@GetMapping(URLConstant.STAFF + "/comment/delete/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(name = "id") Long id) {
		commentService.deleteComment(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	@GetMapping(URLConstant.STAFF + "/comments")
	public String findComment() {
		return "admin/comment/list-comment";
	}

	@PostMapping(URLConstant.STAFF + "/comments")
	public ResponseEntity<ResponseDTO<CommentDTO>> findComment(@RequestBody SearchCommentDTO searchCommentDTO) {
		ResponseDTO<CommentDTO> responseDTO = new ResponseDTO<CommentDTO>();
		responseDTO.setData(commentService.findComment(searchCommentDTO));
		Long total = commentService.countComment(searchCommentDTO);
		responseDTO.setTotalRecords(total);
		responseDTO.setRecordsFiltered(total);

		return new ResponseEntity<ResponseDTO<CommentDTO>>(responseDTO, HttpStatus.OK);
	}
}
