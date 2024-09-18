package com.haruns.service;

import com.haruns.dto.request.LikeRequestDTO;
import com.haruns.entity.Like;
import com.haruns.entity.User;
import com.haruns.entity.Video;
import com.haruns.repository.LikeRepository;
import com.haruns.utility.ConsoleTextUtils;
import com.haruns.utility.ICrud;

import java.util.List;
import java.util.Optional;

public class LikeService  {
	LikeRepository likeRepository;
	VideoService videoService;
	UserService userService;
	private static LikeService instance;
	
	private LikeService() {
		likeRepository=LikeRepository.getInstance();
		videoService=VideoService.getInstance();
		userService=UserService.getInstance();
	}
	public static LikeService getInstance() {
		if (instance == null) {
			instance = new LikeService();
		}
		return instance;
	}
	
	
	public Optional<Like> save(LikeRequestDTO likeRequestDTO) {
		Like like;
		try {
			Optional<Video> videoOpt = videoService.findById(likeRequestDTO.getVideo_id());
			Optional<User> userOpt = userService.findById(likeRequestDTO.getUser_id());
			if (videoOpt.isPresent() && userOpt.isPresent()) {
				like=new Like();
				like.setVideo_id(videoOpt.get().getId());
				like.setUser_id(userOpt.get().getId());
				like.setStatus(likeRequestDTO.getStatus());
				likeRepository.save(like);
				ConsoleTextUtils.printSuccessMessage("Like kaydedildi");
				return Optional.of(like);
	}
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service : Like kaydedilirken hata oluştu : "+e.getMessage());
		}
		return Optional.empty();
	}
	

	public Optional<Like> update(LikeRequestDTO likeRequestDTO) {
		Like like=likeRepository.findById(likeRequestDTO.getId()).get();
		try {
			Optional<Video> videoOpt = videoService.findById(likeRequestDTO.getVideo_id());
			Optional<User> userOpt = userService.findById(likeRequestDTO.getUser_id());
			if (videoOpt.isPresent() && userOpt.isPresent()) {
				like.setStatus(likeRequestDTO.getStatus());
				likeRepository.update(like);
				ConsoleTextUtils.printSuccessMessage("Beğeni durumu değiştirildi.");
				return Optional.of(like);
	}
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service : Like kaydedilirken hata oluştu : "+e.getMessage());
		}
		return Optional.empty();
	}
	
	
	public List<Like> findAll() {
		return likeRepository.findAll();
	}
	
	
	public Optional<Like> findById(Long id) {
		return likeRepository.findById(id);
	}
	
	public boolean isLikeExist(Long user_id,Long video_id) {
		return likeRepository.isLikeExist(user_id,video_id);
	}
	
	public Like findByUserIdAndVideoId(Long user_id,Long video_id) {
		return likeRepository.findByUserIdAndVideoId(user_id,video_id);
	}

	public Long countLikes(Long videoId){
		return likeRepository.countLikes(videoId);
	}

	public Long countDislikes(Long videoId){
		return likeRepository.countDislikes(videoId);
	}
}