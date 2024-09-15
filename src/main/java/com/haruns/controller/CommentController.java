package com.haruns.controller;

import com.haruns.dto.request.CommentRequestDTO;
import com.haruns.dto.response.CommentResponseDTO;
import com.haruns.entity.Comment;
import com.haruns.repository.CommentRepository;
import com.haruns.service.CommentService;
import com.haruns.utility.ICrud;

import java.util.List;
import java.util.Optional;

public class CommentController {

    private static CommentController instance;
    private CommentService commentService;

    private CommentController(){
        commentService = new CommentService();
    }
    public static CommentController getInstance(){
        if(instance == null){
            instance = new CommentController();
        }
        return instance;
    }



    public Optional<CommentResponseDTO> save(CommentRequestDTO commentRequestDTO) {
        return commentService.save(commentRequestDTO);
    }


    public void delete(Long id) {
        commentService.delete(id);
    }


    public void update(CommentRequestDTO commentRequestDTO) {
        commentService.update(commentRequestDTO);
    }


    public List<Comment> findAll() {
        return commentService.findAll();
    }

    public Optional<Comment> findById(Long id) {
        return commentService.findById(id);
    }
}
