package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Comments;
import com.example.engagement_platform.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comments> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comments createComment(Comments comments) {
        return commentRepository.save(comments);
    }

    @Override
    public Comments getCommentById(Long commentId) {
        Optional<Comments> commentFrDb = commentRepository.findById(commentId);
        if (commentFrDb.isPresent()){
            return commentFrDb.get();
        }else {
            throw new RuntimeException("Comment not Found");
        }
    }

    @Override
    public void deleteCommentById(Long commentId) {
        Optional<Comments> commentFromDb = commentRepository.findById(commentId);
        if (commentFromDb.isPresent()){
            commentRepository.deleteById(commentId);
        }else {
            throw new RuntimeException("Comment not found");
        }
    }

    @Override
    public void updateCommentById(Long commentId, Comments comments) {
        Optional<Comments> commentsFrDb = commentRepository.findById(commentId);
        if (commentsFrDb.isPresent()){
            commentRepository.save(comments);
        }else {
            throw new RuntimeException("Comment not Found");
        }
    }
}
