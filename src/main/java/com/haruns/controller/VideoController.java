package com.haruns.controller;

import com.haruns.dto.request.VideoSaveRequestDTO;
import com.haruns.dto.request.VideoUpdateRequestDTO;
import com.haruns.dto.response.VideoResponseDTO;
import com.haruns.entity.Video;
import com.haruns.service.VideoService;
import com.haruns.utility.ConsoleTextUtils;

import java.util.List;
import java.util.Optional;

public class VideoController {
	private static VideoController instance;
	
	VideoService videoService;
	
	public static VideoController getInstance() {
		if(instance ==null) {
			instance =new VideoController();
		}
		return instance;
	}
	
	private VideoController() {
		this.videoService=new VideoService();
	}
	
	public Optional<VideoResponseDTO> save(VideoSaveRequestDTO videoSaveRequestDTO) {
		try {
			return videoService.save(videoSaveRequestDTO);
		}
		catch (Exception e){
			ConsoleTextUtils.printErrorMessage("Controller : Video kaydedilirken hata oluştu : "+e.getMessage());
		}
		return Optional.empty();
	}
	
	public void delete(Long id) {
			videoService.delete(id);
	}
	
	public void update(VideoUpdateRequestDTO videoUpdateRequestDTO) {
			videoService.update(videoUpdateRequestDTO);
	}
	
	public List<Video> findAll() {
		return videoService.findAll();
	}
	public Optional<Video> findById(Long id) {
		return videoService.findById(id);
	}
	public List<Video> getTrendVideos (){
		return videoService.getTrendVideos();
	}
	public List<Video> findVideosByTitle(String baslik){
		return videoService.findVideosByTitle(baslik);
	}
}