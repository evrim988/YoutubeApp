package com.haruns.service;

import com.haruns.entity.CommentGarbage;
import com.haruns.repository.CommentGarbageRepository;

import java.util.List;
import java.util.Optional;

public class CommentGarbageService {
	CommentGarbageRepository commentGarbageRepository;
	
	public CommentGarbageService() {
		commentGarbageRepository=new CommentGarbageRepository();
	}
	
	public Optional<CommentGarbage> save(CommentGarbage commentGarbage) {
		return 	commentGarbageRepository.save(commentGarbage);
	}
	
	public List<CommentGarbage> findAllOldComments(Long comment_id){
		return commentGarbageRepository.findAllOldComments(comment_id);
	}
}