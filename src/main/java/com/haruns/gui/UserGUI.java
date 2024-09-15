package com.haruns.gui;

import com.haruns.controller.UserController;
import com.haruns.controller.VideoController;
import com.haruns.dto.request.UserRequestDTO;
import com.haruns.dto.request.VideoSaveRequestDTO;
import com.haruns.dto.request.VideoUpdateRequestDTO;
import com.haruns.entity.User;
import com.haruns.entity.Video;
import com.haruns.utility.ConsoleTextUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserGUI {
    private UserController userController;
    private static UserGUI getInstance;
    private static VideoController videoController;
    private User user;


    private UserGUI() {
        userController = UserController.getInstance();
        videoController = VideoController.getInstance();
    }


    public static UserGUI getInstance() {
        if (getInstance == null) {
            getInstance = new UserGUI();
        }
        return getInstance;
    }

    public void userModule() {
        userLoginMenuOptions(userLoginMenu());
    }

    public int userLoginMenu() {

        ConsoleTextUtils.printMenuOptions("Giriş Yap", "Geri Dön");
        int secim = ConsoleTextUtils.getIntUserInput("Seçiminiz:");
        return secim;
    }

    public void userLoginMenuOptions(int secim) {
        switch (secim) {
            case 1:
                user = doLogin();
                if (user != null) {
                    userMenuOptions(userMenu());
                }
                userLoginMenuOptions(userLoginMenu());
                break;
            case 2:
                ConsoleTextUtils.printSuccessMessage("Ana menüye dönüyorsunuz.");
                break;
            default:
                ConsoleTextUtils.printErrorMessage("Lütfen geçerli bir seçim yapınız.");
        }
    }

    public int userMenu() {
        ConsoleTextUtils.printTitle("KULLANICI MENÜSÜ");
        ConsoleTextUtils.printMenuOptions("Video ara", "Kanalıma git", "Video ekle", "Videolarımı Görüntüke"
                , "Video Sil", "Oturumu Kapat");
        return ConsoleTextUtils.getIntUserInput("Seçiminiz: ");
    }

    public void userMenuOptions(int secim) {

        switch (secim) {
            case 1:
                videoController.findVideosByTitle(ConsoleTextUtils.getStringUserInput("Ara: "))
                        //todo: beğeni yorum. eklenecek.
                        .forEach(System.out::println);
                userMenuOptions(userMenu());
                break;
            case 2:
                kullaniciAyarlarMenuOptions(kullaniciAyarlarMenu());
                userMenuOptions(userMenu());
                break;
            case 3:
                videoEkle();
                userMenuOptions(userMenu());
                break;
            case 4:
                videoController.findVideosOfUser(user).forEach(System.out::println);
                userMenuOptions(userMenu());
                break;
            case 5:
                videoSil();
                userMenuOptions(userMenu());
                break;
            case 6:
                System.out.println("Ana menüye dönüyorsunuz.");
            default:
                ConsoleTextUtils.printErrorMessage("Lütfen geçerli bir seçim yapınız.");


        }
    }

    public int kullaniciAyarlarMenu() {
        ConsoleTextUtils.printTitle("KANALIM");
        ConsoleTextUtils.printMenuOptions("Beğendiğim videolar", "Şifre değiştir",
                "Video title değiştir", "Video açıklama değiştir", "Geri Dön");
        return ConsoleTextUtils.getIntUserInput("Seçiminiz: ");
    }

    public void kullaniciAyarlarMenuOptions(int secim) {
        switch (secim) {
            case 1:
                //beğendiğim videolar
                break;
            case 2:
                sifreDegistir();
                kullaniciAyarlarMenuOptions(kullaniciAyarlarMenu());
                break;
            case 3:
                videoTitleDegistir();
                kullaniciAyarlarMenuOptions(kullaniciAyarlarMenu());
                break;
            case 4:
                videoAciklamaDegistir();
                kullaniciAyarlarMenuOptions(kullaniciAyarlarMenu());
                break;
            case 5:
                ConsoleTextUtils.printSuccessMessage("Geri dönülüyor.");
                return;
            default:
                System.out.println("Lütfen geçerli seçim yapınız.");
                kullaniciAyarlarMenuOptions(kullaniciAyarlarMenu());
        }
    }

    public void videoSil() {
        Video video = videoSec();
        videoController.delete(video.getId());
    }

    public void videoEkle() {
        VideoSaveRequestDTO videoSaveRequestDTO = new VideoSaveRequestDTO();
        String title = ConsoleTextUtils.getStringUserInput("Başlık giriniz: ");
        String description = ConsoleTextUtils.getStringUserInput("Video açıklaması: ");
        videoSaveRequestDTO.setTitle(title);
        videoSaveRequestDTO.setDescription(description);
        videoSaveRequestDTO.setUsername(user.getUsername());
        videoSaveRequestDTO.setPassword(user.getPassword());
        videoController.save(videoSaveRequestDTO);
    }


    public User doLogin() {
        String username = ConsoleTextUtils.getStringUserInput("Lütfen kullanıcı adınızı giriniz : ");
        String password = ConsoleTextUtils.getStringUserInput("Lütfen şifrenizi giriniz : ");
        Optional<User> optUser = userController.findByUsernameAndPassword(username, password);
        return optUser.orElse(null);
    }

    public User register() {
        UserRequestDTO userRequestDTO;
        String mail = null, username = null;
        String ad = ConsoleTextUtils.getStringUserInput("Lütfen adınızı giriniz : ");
        String soyad = ConsoleTextUtils.getStringUserInput("Lütfen soyadınızı giriniz : ");
        while (true) {
            mail = ConsoleTextUtils.getStringUserInput("Lütfen mail adresinizi giriniz : ");
            if (!userController.isExistEmail(mail)) {
                break;
            }
        }
        while (true) {
            username = ConsoleTextUtils.getStringUserInput("Lütfen kullanıcı adınızı giriniz : ");
            if (!userController.isExistUsername(username)) {
                break;
            }
        }
        String password = ConsoleTextUtils.getStringUserInput("Lütfen sifrenizi giriniz : ");
        userController.save(new UserRequestDTO(ad, soyad, mail, username, password));
        return userController.findByUsernameAndPassword(username, password).get();
    }

    public void sifreDegistir() {
        String yenisifre = ConsoleTextUtils.getStringUserInput("Yeni şifre giriniz: ");
        String yeniSifreAgain = ConsoleTextUtils.getStringUserInput("Şifrenizi tekrar giriniz:");
        if (!yenisifre.equals(yeniSifreAgain)) {
            ConsoleTextUtils.printErrorMessage("Girdiğiniz şifreler uyuşmamaktadır. Lütfen tekrar deneyiniz.");
            sifreDegistir();
        }
        user.setPassword(yenisifre);
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername(user.getUsername());
        userRequestDTO.setEmail(user.getEmail());
        userRequestDTO.setName(user.getName());
        userRequestDTO.setSurname(user.getSurname());
        userRequestDTO.setPassword(yenisifre);
        userController.update(userRequestDTO);
    }

    public void videoTitleDegistir() {
       Video video = videoSec();
        VideoUpdateRequestDTO videoUpdateRequestDTO = new VideoUpdateRequestDTO();
       if(video!=null) {
           String title = ConsoleTextUtils.getStringUserInput("Yeni title: ");
           videoUpdateRequestDTO.setTitle(title);
           videoUpdateRequestDTO.setDescription(video.getTitle());
           videoUpdateRequestDTO.setUsername(user.getUsername());
           videoUpdateRequestDTO.setPassword(user.getPassword());
           videoUpdateRequestDTO.setId(video.getId());
           videoController.update(videoUpdateRequestDTO);
       }
    }

    public void videoAciklamaDegistir() {
        Video video = videoSec();
        VideoUpdateRequestDTO videoUpdateRequestDTO = new VideoUpdateRequestDTO();
        if(video!=null) {
            String description = ConsoleTextUtils.getStringUserInput("Yeni açıklama: ");
            videoUpdateRequestDTO.setTitle(video.getTitle());
            videoUpdateRequestDTO.setDescription(description);
            videoUpdateRequestDTO.setUsername(user.getUsername());
            videoUpdateRequestDTO.setPassword(user.getPassword());
            videoUpdateRequestDTO.setId(video.getId());
            videoController.update(videoUpdateRequestDTO);
        }
    }

    public Video videoSec() {
        while (true) {
            AtomicInteger sayac = new AtomicInteger(1);
            List<Video> videoList = videoController.findVideosOfUser(user);
            videoList.forEach(video -> {
                System.out.println(sayac.getAndIncrement() + " " + video);
            });
            int secim = ConsoleTextUtils.getIntUserInput("Video seç: ");
            try {
                return videoList.get(secim - 1);
            } catch (IndexOutOfBoundsException e) {
                ConsoleTextUtils.printErrorMessage("Lütfen geçerli seçim yapınız.");
            }
        }

    }

}