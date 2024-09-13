package com.haruns.entity;

public class Comment {
	private Long id;
	private Long user_id;
	private Long video_id;
	private String comment;
	
	public Comment() {
	}
	
	public Comment(Long user_id, Long video_id, String comment) {
		this.user_id = user_id;
		this.video_id = video_id;
		this.comment = comment;
	}
	
	public Comment(Long id, Long user_id, Long video_id, String comment) {
		this.id = id;
		this.user_id = user_id;
		this.video_id = video_id;
		this.comment = comment;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	public Long getVideo_id() {
		return video_id;
	}
	
	public void setVideo_id(Long video_id) {
		this.video_id = video_id;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return "Comment{" + "id=" + getId()
				+ ", user_id=" + getUser_id()
				+ ", video_id=" + getVideo_id()
				+ ", comment='" + getComment() + '\'' + '}';
	}
}