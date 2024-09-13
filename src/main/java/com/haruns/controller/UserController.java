package com.haruns.controller;

import com.haruns.dto.request.UserRequestDTO;
import com.haruns.dto.response.UserResponseDTO;
import com.haruns.entity.User;
import com.haruns.service.UserService;
import com.haruns.utility.ConsoleTextUtils;

import java.util.List;
import java.util.Optional;

public class UserController {
	
	private static UserController instance;
	private UserService userService;
	
	private UserController() {
		this.userService = new UserService();
		
	}
	
	public static UserController getInstance() {
		if (instance == null) {
			instance = new UserController();
		}
		return instance;
	}
	
	public Optional<UserResponseDTO> save(UserRequestDTO userRequestDTO) {
		try {
			return userService.save(userRequestDTO);
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Controller : Kullanıcı kaydedilirken hata oluştu : " + e.getMessage());
		}
		return Optional.empty();
	}
	
	public void delete(Long id) {
		userService.delete(id);
	}
	
	public void update(UserRequestDTO userRequestDTO) {
		userService.update(userRequestDTO);
	}
	
	public List<User> findAll() {
		return userService.findAll();
	}
	
	
	public Optional<User> findById(Long id) {
		return userService.findById(id);
	}
	
	public Optional<User> findByUsernameAndPassword(String username, String password) {
		return userService.findByUsernameAndPassword(username, password);
	}
	public boolean isExistUsername(String username){
		return userService.isExistUsername(username);
	}
	public boolean isExistEmail(String email){
		return userService.isExistEmail(email);
	}
	
}