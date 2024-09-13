package com.haruns.entity;

public class Video {
	private Long id;
	private Long user_id;
	private String title;
	private String description;
	private Long views;
	
	public Video() {
	}
	
	public Video(Long user_id, String title, String description) {
		this.user_id = user_id;
		this.title = title;
		this.description = description;
	}
	
	public Video(Long id, Long user_id, String title, String description, Long views) {
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.description = description;
		this.views=views;
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
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getViews() {
		return views;
	}
	
	public void setViews(Long views) {
		this.views = views;
	}
	
	@Override
	public String toString() {
		return "Video{" + "id=" + getId()
				+ ", user_id=" + getUser_id()
				+ ", title='" + getTitle() + '\''
				+ ", description='" + getDescription() + '\''
				+ ", views=" + getViews() + '}';
	}
}