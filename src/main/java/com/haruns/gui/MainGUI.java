package com.haruns.gui;

import com.haruns.controller.VideoController;
import com.haruns.entity.User;
import com.haruns.utility.ConsoleTextUtils;



public class MainGUI {
	VideoController videoController;
	UserGUI userGUI;
	
	public MainGUI() {
		videoController=VideoController.getInstance();
		this.userGUI=UserGUI.getInstance();
	}
	public void mainGUI(){
		mainMenuOptions(mainMenu());
	}
	private int mainMenu(){
			ConsoleTextUtils.printTitle(30,"YOUTUBE");
			ConsoleTextUtils.printMenuOptions("Vidoları Görüntüle","Video Ara","Giriş Yap","Kayıt Ol","Cıkış");
			int secim = ConsoleTextUtils.getIntUserInput("Yapmak istediğiniz islemi seçin :");
		
			return secim;
	}
	
	private void mainMenuOptions(int secim){
		switch (secim){
			case 1:{
				videoController.getTrendVideos().forEach(System.out::println);
				break;
			}
			case 2:{
				String aranacakVideo = ConsoleTextUtils.getStringUserInput("Aranacak videonun adını giriniz : ");
				videoController.findVideosByTitle(aranacakVideo);
				break;
			}
			case 3:{
				User user = userGUI.doLogin();
				if (user != null) {
					// UserGUI'deki UserMenu'ye dönecek.
				}
				break;
			}
			case 4:{
				userGUI.register();
				break;
			}
			case 5:{
				System.out.println("Çıkış yapılıyor...");
				break;
			}
			default:{
				ConsoleTextUtils.printErrorMessage("Lütfen geçerli bir değer giriniz...");
			}
			
		}
	}
}