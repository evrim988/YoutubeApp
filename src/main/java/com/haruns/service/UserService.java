package com.haruns.service;

import com.haruns.dto.request.UserRequestDTO;
import com.haruns.dto.response.UserResponseDTO;
import com.haruns.entity.User;
import com.haruns.entity.Video;
import com.haruns.repository.UserRepository;
import com.haruns.utility.ConsoleTextUtils;
import com.haruns.utility.ICrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService  {
	private final UserRepository userRepository;
	
	
	public UserService() {
		this.userRepository = new UserRepository();
	}
	
	
	public Optional<UserResponseDTO> save(UserRequestDTO userRequestDTO) {
		User user;
		Optional<User> userOptional;
		UserResponseDTO responseDTO=new UserResponseDTO();
		try {
			if (userRepository.isExistUsername(userRequestDTO.getUsername())&& userRepository.isExistEmail(userRequestDTO.getEmail())) {
				ConsoleTextUtils.printErrorMessage("Kullanıcı adı veya email zaten mevcut.");
			}
			user=new User();
			user.setName(userRequestDTO.getName());
			user.setSurname(userRequestDTO.getSurname());
			user.setEmail(userRequestDTO.getEmail());
			user.setUsername(userRequestDTO.getUsername());
			user.setPassword(userRequestDTO.getPassword());
			userOptional=userRepository.save(user);
			ConsoleTextUtils.printSuccessMessage(user.getUsername()+" kullanıcı adlı kullanıcı kaydedildi.");
			responseDTO.setEmail(userOptional.get().getEmail());
			responseDTO.setUsername(userOptional.get().getUsername());
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service : Kullanıcı kaydedilirken hata oluştu : "+e.getMessage());
		}
		return Optional.of(responseDTO);
	}
	
	
	public void delete(Long id) {
		try{
			userRepository.delete(id);
			ConsoleTextUtils.printSuccessMessage("Kullanıcı silindi.");
		}
		catch (Exception e){
			ConsoleTextUtils.printErrorMessage("Service : Kullanıcı silinirken hata oluştu : "+e.getMessage());
		}
	}
	
	
	public void update(UserRequestDTO userRequestDTO) {
		User user;
		try {
			if (userRepository.isUsernameAndMailExist(userRequestDTO.getUsername(), userRequestDTO.getEmail())) {
				Optional<User> optionalUser = userRepository.findByUsername(userRequestDTO.getUsername());
				if (optionalUser.isPresent()){
					user = optionalUser.get();
					user.setName(userRequestDTO.getName());
					user.setSurname(userRequestDTO.getSurname());
					user.setEmail(userRequestDTO.getEmail());
					user.setUsername(userRequestDTO.getUsername());
					user.setPassword(userRequestDTO.getPassword());
					userRepository.update(user);
					ConsoleTextUtils.printSuccessMessage(user.getUsername()+" kullanıcı adlı kullanıcı bilgileri güncellendi.");
				}
			}
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service : Kullanıcı bilgileri güncellenirken hata oluştu : "+e.getMessage());
		}
	
	}
	
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public Optional<User> findByUsernameAndPassword(String username,String password){
		
		Optional<User> optUser = userRepository.findByUsernameAndPassword(username, password);
		if (optUser.isEmpty()){
			ConsoleTextUtils.printErrorMessage("Kullanıcı adı veya sifre hatalı.");
		}
		else {
			ConsoleTextUtils.printSuccessMessage("Hoşgeldiniz, "+username);
		}
		return optUser;
	}
	
	public boolean isExistUsername(String username){
		if (userRepository.isExistUsername(username))
		{
			ConsoleTextUtils.printErrorMessage("Bu kullanıcı adı zaten kayıtlı.");
		}
		return userRepository.isExistUsername(username);
	}
	public boolean isExistEmail(String email){
		if (userRepository.isExistEmail(email))
		{
			ConsoleTextUtils.printErrorMessage("Bu mail adresi zaten kayıtlı.");
		}
		return userRepository.isExistEmail(email);
	}
	public List<Video>  getLikedVideosOfUser(User user){
		List<Video> likedVideosOfUser = userRepository.getLikedVideosOfUser(user);
		if (likedVideosOfUser.isEmpty()){
			ConsoleTextUtils.printErrorMessage("Kullanıcının beğendiği video yok.");
		}
		return likedVideosOfUser;
	}
}