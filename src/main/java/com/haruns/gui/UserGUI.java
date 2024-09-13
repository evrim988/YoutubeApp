package com.haruns.gui;

import com.haruns.controller.UserController;
import com.haruns.dto.request.UserRequestDTO;
import com.haruns.entity.User;
import com.haruns.utility.ConsoleTextUtils;

import java.util.Optional;

public class UserGUI {
	private UserController userController;
	private static UserGUI getInstance;
	
	
	private UserGUI() {
		this.userController=UserController.getInstance();
	}
	
	
	public static UserGUI getInstance() {
		if (getInstance == null) {
			getInstance = new UserGUI();
		}
		return getInstance;
	}
	
	public User doLogin(){
		String username = ConsoleTextUtils.getStringUserInput("Lütfen kullanıcı adınızı giriniz : ");
		String password = ConsoleTextUtils.getStringUserInput("Lütfen şifrenizi giriniz : ");
		Optional<User> optUser = userController.findByUsernameAndPassword(username, password);
		return optUser.orElse(null);
	}
	
	public User register(){
		UserRequestDTO userRequestDTO;
		String mail=null,username=null;
		String ad = ConsoleTextUtils.getStringUserInput("Lütfen adınızı giriniz : ");
		String soyad = ConsoleTextUtils.getStringUserInput("Lütfen soyadınızı giriniz : ");
		while (true){
			mail = ConsoleTextUtils.getStringUserInput("Lütfen mail adresinizi giriniz : ");
			if (!userController.isExistEmail(mail)){
				break;
			}
		}
		while (true){
			username = ConsoleTextUtils.getStringUserInput("Lütfen kullanıcı adınızı giriniz : ");
			if (!userController.isExistUsername(username)){
				break;
			}
		}
		String password = ConsoleTextUtils.getStringUserInput("Lütfen sifrenizi giriniz : ");
		userController.save(new UserRequestDTO(ad, soyad, mail, username, password));
		return userController.findByUsernameAndPassword(username, password).get();
	}
}