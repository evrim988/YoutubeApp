package com.haruns.service;

import com.haruns.dto.request.CommentRequestDTO;
import com.haruns.dto.response.CommentResponseDTO;
import com.haruns.entity.Comment;
import com.haruns.entity.User;
import com.haruns.repository.CommentRepository;
import com.haruns.utility.ConsoleTextUtils;
import com.haruns.utility.ICrud;

import java.util.List;
import java.util.Optional;

public class CommentService {
	private CommentRepository commentRepository;
	private UserService userService;
	
	
	public CommentService() {
		this.commentRepository=new CommentRepository();
		this.userService=new UserService();
	}
	
	public Optional<CommentResponseDTO> save(CommentRequestDTO commentRequestDTO) {
		Comment comment;
		Optional<User> userOptional;
		CommentResponseDTO responseDTO=new CommentResponseDTO();
		try {
			Optional<User> userOpt =
					userService.findByUsernameAndPassword(commentRequestDTO.getUsername(), commentRequestDTO.getPassword());
			if (userOpt.isPresent()) {
				comment=new Comment();
				comment.setComment(commentRequestDTO.getComment());
				comment.setUser_id(userOpt.get().getId());
				commentRepository.save(comment);
				ConsoleTextUtils.printSuccessMessage(comment.getComment()+" yorumu eklendi.");
				responseDTO.setComment(comment.getComment());
				responseDTO.setUsername(userOpt.get().getUsername());
			}
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service : Yorum kaydedilirken hata oluştu : "+e.getMessage());
		}
		return Optional.of(responseDTO);
	}
	
	
	public void delete(Long id) {
	
	}
	
	
	public void update(CommentRequestDTO commentRequestDTO) {
	
	}
	
	
	public List<Comment> findAll() {
		return List.of();
	}
	
	
	public Optional<Comment> findById(Long id) {
		return Optional.empty();
	}
}