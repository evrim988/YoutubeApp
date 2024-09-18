package com.haruns.controller;

import com.haruns.entity.CommentGarbage;
import com.haruns.service.CommentGarbageService;

import java.util.List;
import java.util.Optional;

public class CommentGarbageController {
	CommentGarbageService commentGarbageService;
	private static CommentGarbageController instance;
	
	public static CommentGarbageController getInstance(){
		if(instance == null){
			instance = new CommentGarbageController();
		}
		return instance;
	}
	
	public CommentGarbageController() {
		commentGarbageService=CommentGarbageService.getInstance();
	}
	public Optional<CommentGarbage> save(CommentGarbage commentGarbage) {
		return 	commentGarbageService.save(commentGarbage);
	}
	
	public List<CommentGarbage> findAllOldComments(Long comment_id){
		return commentGarbageService.findAllOldComments(comment_id);
	}
}