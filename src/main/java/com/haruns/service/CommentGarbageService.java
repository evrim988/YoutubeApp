package com.haruns.service;

import com.haruns.entity.CommentGarbage;
import com.haruns.repository.CommentGarbageRepository;
import com.haruns.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

public class CommentGarbageService {
	CommentGarbageRepository commentGarbageRepository;
	private static CommentGarbageService instance;
	
	private CommentGarbageService() {
		commentGarbageRepository=CommentGarbageRepository.getInstance();
	}

	public static CommentGarbageService getInstance() {
		if (instance == null) {
			instance = new CommentGarbageService();
		}
		return instance;
	}
	
	public Optional<CommentGarbage> save(CommentGarbage commentGarbage) {
		return 	commentGarbageRepository.save(commentGarbage);
	}
	
	public List<CommentGarbage> findAllOldComments(Long comment_id){
		return commentGarbageRepository.findAllOldComments(comment_id);
	}
}