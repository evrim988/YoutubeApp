package com.haruns.model;

import com.haruns.controller.CommentController;
import com.haruns.controller.LikeController;
import com.haruns.controller.UserController;
import com.haruns.controller.VideoController;
import com.haruns.entity.*;
import com.haruns.entity.Video;
import com.haruns.utility.ConsoleTextUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoModel {
    public CommentController commentController = CommentController.getInstance();
    public UserController userController = UserController.getInstance();
    public LikeController  likeController = LikeController.getInstance();
    public VideoController videoController = VideoController.getInstance();

    public String title;
    public String description;
    public String username;
    public Long views;
    public Long likes;
    public Long dislikes;
    public Long commentCount;
    public List<Comment> commentList;
    private int yorumSayisi=5;
    private boolean isFullDescriptionShown = false;
    
    
    
    
    public VideoModel(User user,Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.username = userController.findById(video.getUser_id()).get().getUsername();
        this.views = video.getViews();
        this.likes = likeController.countLikes(video.getId());
        this.dislikes = likeController.countDislikes(video.getId());
        this.commentCount = commentController.countCommentsOfVideo(video.getId());
        commentList = commentController.findCommentOfVideo(video.getId());
        

    }
    private void printDescriptionWithoutBreakingWords(String description, int lineLength) {
        String[] words = description.split(" "); // Kelimeleri boşluklara göre bölüyoruz
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // Eğer bu kelimeyi eklediğimizde satırın uzunluğu limitin altındaysa ekle
            if (currentLine.length() + word.length() + 1 <= lineLength) {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            } else {
                // Satır limitine ulaşıldığında önce mevcut satırı yazdır, sonra yeni satıra geç
                System.out.println(currentLine.toString());
                currentLine = new StringBuilder(word); // Yeni satıra kelimeyi koy
            }
        }

        // Son satırı da yazdır (eğer varsa)
        if (currentLine.length() > 0) {
            System.out.println(currentLine.toString());
        }
    }
    
    private void printFirstLineOfDescription(String description) {
        // Açıklamanın sadece ilk satırını gösterecek şekilde kelimeleri bölme
        String[] words = description.split(" ");
        StringBuilder firstLine = new StringBuilder();
        for (String word : words) {
            if (firstLine.length() + word.length() + 1 <= 50) { // İlk satır uzunluğu
                if (firstLine.length() > 0) {
                    firstLine.append(" ");
                }
                firstLine.append(word);
            } else {
                break;
            }
        }
        System.out.println(firstLine.toString());
    }
    
    public void displayVideo() {
        int secim=-1;
        System.out.println("*************************************************");
        for (int i = 0; i < 10; i++) {
            System.out.println("*\t\t\t\t\t\t\t\t\t\t\t\t*");
        }
        System.out.println("*************************************************");
        System.out.println(title);

        // %30d ile views'ı 30 karakter genişliğinde sağa yaslıyoruz
        System.out.printf("%s\t\t\uD83D\uDC4D:%d \uD83D\uDC4E:%d\t\t\t\t\t📺:%2d\n", username, likes, dislikes, views);
        System.out.println("-------------------------------------------------");
      
       
        if (isFullDescriptionShown) {
            printDescriptionWithoutBreakingWords(description, 50);
        } else {
            printFirstLineOfDescription(description);
        }
        
        System.out.println("-------------------------------------------------");
       
            AtomicInteger siraNumarasi = new AtomicInteger(1);
            commentList.stream().sorted((c1, c2) -> c2.getId().compareTo(c1.getId())).limit(yorumSayisi).forEach(c->{
                User userCommented = userController.findById(c.getUser_id()).get();
                System.out.println(siraNumarasi.getAndIncrement()+" - "+userCommented.getUsername()+"\t\t"+c.getComment());
            });
            System.out.println("-------------------------------------------------");
            if (isFullDescriptionShown) {
                ConsoleTextUtils.printMenuOptions("Daha Az Göster", "Devam Et", "Daha Fazla Yorum Göster");
            } else {
                ConsoleTextUtils.printMenuOptions("Daha Fazla Göster", "Devam Et", "Daha Fazla Yorum Göster");
            }
            secim = ConsoleTextUtils.getIntUserInput("Seciminiz: ");
            switch (secim) {
               case 1: {
                    isFullDescriptionShown = !isFullDescriptionShown;
                    displayVideo();
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    yorumSayisi+=5;
                    displayVideo();
                    break;
                }
            }
            System.out.println();
    }
}