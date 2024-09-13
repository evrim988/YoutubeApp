package com.haruns.entity;

public class Like {
	private Long id;
	private Long user_id;
	private Long video_id;
	
	public Like() {
	}
	
	public Like(Long user_id, Long video_id) {
		this.user_id = user_id;
		this.video_id = video_id;
	}
	
	public Like(Long id, Long user_id, Long video_id) {
		this.id = id;
		this.user_id = user_id;
		this.video_id = video_id;
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
	
}