package com.haruns.gui;

import com.haruns.controller.VideoController;
import com.haruns.entity.User;
import com.haruns.entity.Video;
import com.haruns.utility.ConsoleTextUtils;



public class MainGUI {
	VideoController videoController=VideoController.getInstance();
	UserGUI userGUI=UserGUI.getInstance();
	User user=null;
	private static MainGUI getInstance;
	
	
	
	public static MainGUI getInstance() {
		if(getInstance==null) {
			getInstance=new MainGUI();
		}
		return getInstance;
	}
	
	public void mainGUI(){
		mainMenuOptions(mainMenu());
	}
	private int mainMenu(){
			ConsoleTextUtils.printTitle(30,"YOUTUBE");
			ConsoleTextUtils.printMenuOptions("Vidoları Görüntüle","Video Ara","Kullanıcı Sayfası","Kayıt Ol","Cıkış");

        return ConsoleTextUtils.getIntUserInput("Yapmak istediğiniz islemi seçin :");
	}
	
	private void mainMenuOptions(int secim){
		switch (secim){
			case 1:{
				videoController.getTrendVideos().forEach(System.out::println);
				mainMenuOptions(mainMenu());
				break;
			}
			case 2:{
				String aranacakVideo = ConsoleTextUtils.getStringUserInput("Aranacak videonun adını giriniz : ");
				Video video = userGUI.goruntulenmeArttir(videoController.findVideosByTitle(aranacakVideo));
				userGUI.videoIzleOptions(user, userGUI.videoIzle(),video);
				
				mainMenuOptions(mainMenu());
				break;
			}
			case 3:{

				userGUI.userModule();
				mainMenuOptions(mainMenu());
				break;
			}
			case 4:{
				userGUI.register();
				mainMenuOptions(mainMenu());
				break;
			}
			case 5:{
				System.out.println("Çıkış yapılıyor...");
				break;
			}
			default:{
				ConsoleTextUtils.printErrorMessage("Lütfen geçerli bir değer giriniz...");
				mainMenuOptions(mainMenu());
			}
			
		}
	}
}