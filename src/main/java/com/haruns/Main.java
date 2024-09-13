package com.haruns;

import com.haruns.dto.request.UserRequestDTO;
import com.haruns.dto.response.UserResponseDTO;
import com.haruns.entity.Comment;
import com.haruns.entity.Like;
import com.haruns.entity.User;
import com.haruns.entity.Video;
import com.haruns.gui.MainGUI;
import com.haruns.repository.CommentRepository;
import com.haruns.repository.LikeRepository;
import com.haruns.repository.UserRepository;
import com.haruns.repository.VideoRepository;
import com.haruns.service.UserService;

import java.util.Optional;

public class Main {
	public static void main(String[] args) {

//		UserRepository userRepository=new UserRepository();
//		VideoRepository videoRepository=new VideoRepository();
//		CommentRepository commentRepository=new CommentRepository();
//		LikeRepository likeRepository=new LikeRepository();
//		UserService userService=new UserService();
//		UserRequestDTO userRequestDTO=new UserRequestDTO();
//		userRequestDTO.setName("Harun");
//		userRequestDTO.setSurname("SAKIN");
//		userRequestDTO.setEmail("harunsakin@gmail.com");
//		userRequestDTO.setUsername("harunsakin");
//		userRequestDTO.setPassword("123456");
//		//Optional<UserResponseDTO> save = userService.save(userRequestDTO);
//		userRequestDTO.setPassword("qweqwe");
		System.out.println("Hello World!");

		MainGUI mainGUI = new MainGUI();
		mainGUI.mainGUI();
		
	}
}