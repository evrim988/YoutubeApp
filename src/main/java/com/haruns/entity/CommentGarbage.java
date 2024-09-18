package com.haruns.entity;

public class CommentGarbage {
	private Long id;
	private Long comment_id;
	private String comment;
	
	public CommentGarbage() {
	}
	
	public CommentGarbage(Long comment_id, String comment) {
		this.comment_id = comment_id;
		this.comment = comment;
	}
	
	public CommentGarbage(Long id, Long comment_id, String comment) {
		this.id = id;
		this.comment_id = comment_id;
		this.comment = comment;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getComment_id() {
		return comment_id;
	}
	
	public void setComment_id(Long comment_id) {
		this.comment_id = comment_id;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}