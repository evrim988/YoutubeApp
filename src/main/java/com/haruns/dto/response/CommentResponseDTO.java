package com.haruns.dto.response;

public class CommentResponseDTO {
	private String username;
	private String comment;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return "CommentResponseDTO{" + "username='" + getUsername() + '\''
				+ ", comment='" + getComment() + '\'' + '}';
	}
}