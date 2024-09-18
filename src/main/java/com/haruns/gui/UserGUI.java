package com.haruns.gui;

import com.haruns.controller.*;
import com.haruns.dto.request.*;
import com.haruns.entity.*;
import com.haruns.model.UserModel;
import com.haruns.model.VideoModel;
import com.haruns.utility.ConsoleTextUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserGUI {
    private UserController userController=UserController.getInstance();
    private static UserGUI getInstance;
    private static VideoController videoController=VideoController.getInstance();
    private static CommentController commentController=CommentController.getInstance();
    private static LikeController likeController=LikeController.getInstance();
    private static CommentGarbageController commentGarbageController=CommentGarbageController.getInstance();
   // private  MainGUI mainGUI=MainGUI.getInstance();
    private  User user;


    private UserGUI() {
//        userController = UserController.getInstance();
//        videoController = VideoController.getInstance();
//        commentController = CommentController.getInstance();
//        likeController=LikeController.getInstance();
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
        ConsoleTextUtils.printMenuOptions("Video izle", "Kanalıma git", "Video ekle", "Videolarımı Görüntüle"
                , "Video Sil", "Kullanıcı Ara" ,"Oturumu Kapat");
        return ConsoleTextUtils.getIntUserInput("Seçiminiz: ");
    }

    public void userMenuOptions(int secim) {

        switch (secim) {
            case 1:
                Video video = getVideo(videoController.findVideosByTitle(ConsoleTextUtils.getStringUserInput("Ara: ")));
                if(video!=null) {
                    new VideoModel(user,video).displayVideo();
                    videoIzleOptions(user,videoIzle(),video);
                }
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
            case 6:{
                User user1 = findUser();
                if(user1!=null){
                    UserModel userModel = new UserModel(user1);
                    userMenuOptions(userMenu());
                    userModel.displayUser();
                }
                else {
                    ConsoleTextUtils.printErrorMessage("Kullanıcı bulunamadı.");
                }
                
                break;
            }
            case 7:
                System.out.println("Ana menüye dönüyorsunuz.");
            default:
                ConsoleTextUtils.printErrorMessage("Lütfen geçerli bir seçim yapınız.");


        }
    }
    public User findUser(){
        String kullanici = ConsoleTextUtils.getStringUserInput("Ara: ");
        Optional<User> byUsername = userController.findByUsername(kullanici);
	    if (byUsername.isPresent()) {
		    return byUsername.get();
	    }
        return null;
    }
    public int videoIzle() {
        int secim=-1;
            ConsoleTextUtils.printMenuOptions("Beğen Menüsü", "Yorum Yap","Video yorumlarını göster", "Devam et");
            secim = ConsoleTextUtils.getIntUserInput("Seciminiz: ");
        return secim;
    }
    
    public Video getVideo(List<Video> videosByTitle) {
        Video video = videoSec(videosByTitle);
        if(video==null){
            return null;
        }
        videoController.goruntulenmeArttir(video);
        return video;
    }
    
    public void videoIzleOptions(User kullanici, int secim, Video video) {
        switch (secim) {
            case 1:
                if (kullanici!=null){
                begen(kullanici, video);
                }
                else {
                    String kullaniciAdi = ConsoleTextUtils.getStringUserInput("Kullanıcı adınız : ");
                    String sifre = ConsoleTextUtils.getStringUserInput("Parolanız : ");
                    userController.findByUsernameAndPassword(kullaniciAdi, sifre).ifPresentOrElse((user1 -> {
                        begen(user1, video);
                    }), () -> userMenuOptions(6));
                }
                break;
            case 2:
                if (kullanici!=null){
                    yorumYap(video);
                }
                else {
                    String username = ConsoleTextUtils.getStringUserInput("Kullanıcı adınız : ");
                    String pw = ConsoleTextUtils.getStringUserInput("Parolanız : ");
                    user = userController.findByUsernameAndPassword(username, pw).get();
                    userController.findByUsernameAndPassword(username, pw).ifPresentOrElse((user1 -> {
                        yorumYap(video);
                    }), () -> userMenuOptions(6));
                }
                break;
            case 3:
                commentController.findCommentOfVideo(video.getId()).forEach(System.out::println);
                break;
        }
    }
    
    public void begen(User user,Video video){
        LikeRequestDTO likeRequestDTO=new LikeRequestDTO();
        likeRequestDTO.setUser_id(user.getId());
        likeRequestDTO.setVideo_id(video.getId());
        boolean likeExist = likeController.isLikeExist(user.getId(), video.getId());
        if (likeExist){
            Like like = likeController.findByUserIdAndVideoId(user.getId(), video.getId());
            likeRequestDTO.setId(like.getId());
            if (like.getStatus()==1){
                ConsoleTextUtils.printMenuOptions("Beğeniyi geri çek","Dislike et","Devam et");
                int secim = ConsoleTextUtils.getIntUserInput("Seciminiz: ");
                switch (secim){
                    case 1:{
                        
                        likeRequestDTO.setStatus(4);
                        likeController.update(likeRequestDTO);
                        ConsoleTextUtils.printSuccessMessage("Begeni geri çekildi");
                        break;
                    }
                    case 2:{
                        likeRequestDTO.setStatus(5);
                        likeController.update(likeRequestDTO);
                        ConsoleTextUtils.printSuccessMessage("Dislike Atıldı");
                        break;
                    }
                }
            }
            else if (like.getStatus()==0) {
                ConsoleTextUtils.printMenuOptions("Beğen","Dislike et","Devam et");
                int secim = ConsoleTextUtils.getIntUserInput("Seciminiz: ");
                switch (secim){
                    case 1:{
                        likeRequestDTO.setStatus(1);
                        likeController.update(likeRequestDTO);
                        ConsoleTextUtils.printSuccessMessage("Video beğenildi.");
                        break;
                    }
                    case 2:{
                        likeRequestDTO.setStatus(-1);
                        likeController.update(likeRequestDTO);
                        ConsoleTextUtils.printSuccessMessage("Dislike Atıldı");
                        break;
                    }
                }
            }
            else { // -1 ise
                ConsoleTextUtils.printMenuOptions("Dislike'ı geri çek","Beğen","Devam et");
                int secim = ConsoleTextUtils.getIntUserInput("Seciminiz: ");
                switch (secim){
                    case 1:{
                        likeRequestDTO.setStatus(2);
                        likeController.update(likeRequestDTO);
                        ConsoleTextUtils.printSuccessMessage("Dislike geri çekildi.");
                        break;
                    }
                    case 2:{
                        likeRequestDTO.setStatus(3);
                        likeController.update(likeRequestDTO);
                        ConsoleTextUtils.printSuccessMessage("Video beğenildi.");
                        break;
                    }
                }
            }
        }
        else {
            ConsoleTextUtils.printMenuOptions("Beğen","Dislike","Devam et");
            int secim = ConsoleTextUtils.getIntUserInput("Seciminiz: ");
            switch (secim){
                case 1:{
                    likeRequestDTO.setStatus(1);
                    likeController.save(likeRequestDTO);
                    ConsoleTextUtils.printSuccessMessage("Video beğenildi.");
                    break;
                }
                case 2:{
                    likeRequestDTO.setStatus(-1);
                    likeController.save(likeRequestDTO);
                    ConsoleTextUtils.printSuccessMessage("Dislike atıldı.");
                    break;
                }
            }
        }
    }

    public int kullaniciAyarlarMenu() {
        ConsoleTextUtils.printTitle("KANALIM");
        ConsoleTextUtils.printMenuOptions("Beğendiğim videolar", "Şifre değiştir",
                "Video title değiştir", "Video açıklama değiştir","Attığım yorumu düzenle", "Geri Dön");
        return ConsoleTextUtils.getIntUserInput("Seçiminiz: ");
    }

    public void kullaniciAyarlarMenuOptions(int secim) {
        switch (secim) {
            case 1:
                userController.getLikedVideosOfUser(user).forEach(System.out::println);
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
            case 5:{
                yorumDuzenle();
                kullaniciAyarlarMenuOptions(kullaniciAyarlarMenu());
                break;
            }
            case 6:
                ConsoleTextUtils.printSuccessMessage("Geri dönülüyor.");
                return;
            default:
                System.out.println("Lütfen geçerli seçim yapınız.");
                kullaniciAyarlarMenuOptions(kullaniciAyarlarMenu());
        }
    }
    
    private void yorumDuzenle() {
        List<Comment> allCommentsOfUser = userController.getAllCommentsOfUser(user);
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        Comment comment = yorumSec(allCommentsOfUser);
        if (comment != null) {
            String yorum = ConsoleTextUtils.getStringUserInput("Yorumunuzu giriniz: ");
            commentRequestDTO.setComment(yorum);
            commentRequestDTO.setId(comment.getId());
            commentRequestDTO.setVideoId(comment.getVideo_id());
            commentRequestDTO.setUsername(user.getUsername());
            commentRequestDTO.setPassword(user.getPassword());
            commentGarbageController.save(new CommentGarbage(comment.getId(), comment.getComment()));
            commentController.update(commentRequestDTO);
        }
        else {
            ConsoleTextUtils.printErrorMessage("Yorum bulunamadı.");
        }
    }
    
    public void yorumYap(Video video) {
        CommentRequestDTO commentRequestDTO  = new CommentRequestDTO();
        String yorum = ConsoleTextUtils.getStringUserInput("Yorumunuz: ");
        commentRequestDTO.setComment(yorum);
        commentRequestDTO.setUsername(user.getUsername());
        commentRequestDTO.setPassword(user.getPassword());
        commentRequestDTO.setVideoId(video.getId());
        commentController.save(commentRequestDTO);
    }

    public void videoSil() {
        Video video = videoSec(videoController.findVideosOfUser(user));
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
        Video video = videoSec(videoController.findVideosOfUser(user));
        VideoUpdateRequestDTO videoUpdateRequestDTO = new VideoUpdateRequestDTO();
        if (video != null) {
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
        Video video = videoSec(videoController.findVideosOfUser(user));
        VideoUpdateRequestDTO videoUpdateRequestDTO = new VideoUpdateRequestDTO();
        if (video != null) {
            String description = ConsoleTextUtils.getStringUserInput("Yeni açıklama: ");
            videoUpdateRequestDTO.setTitle(video.getTitle());
            videoUpdateRequestDTO.setDescription(description);
            videoUpdateRequestDTO.setUsername(user.getUsername());
            videoUpdateRequestDTO.setPassword(user.getPassword());
            videoUpdateRequestDTO.setId(video.getId());
            videoController.update(videoUpdateRequestDTO);
        }
    }

    public Video videoSec(List<Video> videoList) {
        while (true) {
            AtomicInteger sayac = new AtomicInteger(1);
            if(videoList.isEmpty()){
                System.out.println("Aramaya uygun video bulunamadı.");
                return null;
            }
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
    
    public Comment yorumSec(List<Comment> commentList) {
        while (true) {
            AtomicInteger sayac = new AtomicInteger(1);
            if(commentList.isEmpty()){
                System.out.println("Aramaya uygun video bulunamadı.");
                return null;
            }
            commentList.forEach(comment -> {
                System.out.println(sayac.getAndIncrement() + " " + comment);
            });
            int secim = ConsoleTextUtils.getIntUserInput("Yorum seç: ");
            try {
                return commentList.get(secim - 1);
            } catch (IndexOutOfBoundsException e) {
                ConsoleTextUtils.printErrorMessage("Lütfen geçerli seçim yapınız.");
            }
        }
        
    }

}