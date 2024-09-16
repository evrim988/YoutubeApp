package com.haruns.model;

import com.haruns.controller.CommentController;
import com.haruns.controller.LikeController;
import com.haruns.controller.UserController;
import com.haruns.controller.VideoController;
import com.haruns.entity.*;
import com.haruns.entity.Video;

import java.util.List;

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



    public VideoModel(User user,Video video) {
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.username = user.getUsername();
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


    public void displayVideo() {
        System.out.println("*************************************************");
        for (int i = 0; i < 10; i++) {
            System.out.println("*\t\t\t\t\t\t\t\t\t\t\t\t*");
        }
        System.out.println("*************************************************");
        System.out.println(title);

        // %30d ile views'ı 30 karakter genişliğinde sağa yaslıyoruz
        System.out.printf("%s\t\t\uD83D\uDC4D:%d \uD83D\uDC4E:%d\t\t\t\t\t📺:%6d\n", username, likes, dislikes, views);
        System.out.println("-------------------------------------------------");
        printDescriptionWithoutBreakingWords(description, 50);
        System.out.println("-------------------------------------------------");
        commentList.stream().limit(5).forEach(c->{
            User userCommented = userController.findById(c.getUser_id()).get();
            System.out.println(userCommented.getUsername());
            System.out.println(c.getComment());
        });
        System.out.println();
    }

}

