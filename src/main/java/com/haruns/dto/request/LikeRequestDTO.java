package com.haruns.dto.request;

public class LikeRequestDTO {
	private Long id;
	private Long user_id;
	private Long video_id;
	private int status;
	
	public LikeRequestDTO() {
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
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