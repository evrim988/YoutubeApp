package com.haruns.service;

import com.haruns.dto.request.VideoRequestDTO;
import com.haruns.dto.request.VideoSaveRequestDTO;
import com.haruns.dto.request.VideoUpdateRequestDTO;
import com.haruns.dto.response.UserResponseDTO;
import com.haruns.dto.response.VideoResponseDTO;
import com.haruns.entity.User;
import com.haruns.entity.Video;
import com.haruns.repository.VideoRepository;
import com.haruns.utility.ConsoleTextUtils;
import com.haruns.utility.ICrud;

import java.util.List;
import java.util.Optional;

public class VideoService   {
	private VideoRepository videoRepository;
	private UserService userService;
	
	public VideoService() {
		this.videoRepository=new VideoRepository();
		this.userService=new UserService();
	}
	
	public Optional<VideoResponseDTO> save(VideoSaveRequestDTO videoSaveRequestDTO) {
		Video video;
		Optional<User> userOptional;
		VideoResponseDTO responseDTO=new VideoResponseDTO();
		try {
			Optional<User> userOpt =
					userService.findByUsernameAndPassword(videoSaveRequestDTO.getUsername(), videoSaveRequestDTO.getPassword());
			if (userOpt.isPresent()) {
				video=new Video();
				video.setTitle(videoSaveRequestDTO.getTitle());
				video.setDescription(videoSaveRequestDTO.getDescription());
				video.setUser_id(userOpt.get().getId());
				videoRepository.save(video);
				ConsoleTextUtils.printSuccessMessage(video.getTitle()+"başlıklı video eklendi.");
				responseDTO.setTitle(video.getTitle());
				responseDTO.setDescription(video.getDescription());
				responseDTO.setViews(video.getViews());
				responseDTO.setUsername(userOpt.get().getUsername());
			}
		}
		catch (Exception e) {
			ConsoleTextUtils.printErrorMessage("Service : Video kaydedilirken hata oluştu : "+e.getMessage());
		}
		return Optional.of(responseDTO);
	}
	
	
	public void delete(Long id) {
	videoRepository.delete(id);
	ConsoleTextUtils.printSuccessMessage("Video silindi.");
	}
	
	
	public void update(VideoUpdateRequestDTO videoUpdateRequestDTO) {
		Video video;
		try {
			Optional<User> userOpt =
					userService.findByUsernameAndPassword(videoUpdateRequestDTO.getUsername(), videoUpdateRequestDTO.getPassword());
			if (userOpt.isPresent()) {
				video=new Video();
				video.setId(videoUpdateRequestDTO.getId());
				video.setTitle(videoUpdateRequestDTO.getTitle());
				video.setDescription(videoUpdateRequestDTO.getDescription());
				video.setUser_id(userOpt.get().getId());
				video.setViews(videoUpdateRequestDTO.getViews());
				videoRepository.update(video);
				ConsoleTextUtils.printSuccessMessage("Video güncellendi");
			}
		}
		catch (Exception e){
			ConsoleTextUtils.printErrorMessage("Service : Video güncellenirken hata oluştu : "+e.getMessage());
		}
		
		
	}
	public List<Video> findAll() {
		return videoRepository.findAll();
	}
	
	
	public Optional<Video> findById(Long id) {
		return videoRepository.findById(id);
	}
	
	public List<Video> getTrendVideos (){
		
		List<Video> trendVideos = videoRepository.getTrendVideos();
		if (trendVideos.isEmpty()){
			ConsoleTextUtils.printErrorMessage("Video listesi boş olduğu için görüntülenecek video bulunamadı!");
		}
		return trendVideos;
	}
	
	public List<Video> findVideosByTitle(String baslik){
		List<Video> videosByTitle = videoRepository.findVideosByTitle(baslik);
		if (videosByTitle.isEmpty()){
			ConsoleTextUtils.printErrorMessage("Aradığınız video bulunamadı!");
		}
		return videosByTitle;
	}

	public List<Video> findVideosOfUser(User user){
		List<Video> videosOfUser = videoRepository.findVideosOfUser(user);
		if(videosOfUser.isEmpty()){
			System.out.println("Yüklediğiniz bir video bulunmamaktadır.");
		}
		return videosOfUser;
	}
}