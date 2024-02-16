package com.repring.be;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MusicRepository musicRepository;


    public String create(CommentDTO.CreateRequest request) {
        Music music = musicRepository.findById(request.getMusicId()).orElseThrow();
        Comment newComment = Comment.from(request, music);
        commentRepository.save(newComment);

        return "ok";
    }

    public List<CommentDTO.Response> readAll(Long musicId) {
        Music music = musicRepository.findById(musicId).orElseThrow();
        List<Comment> commentList = commentRepository.findByMusic(music);
        List<CommentDTO.Response> commentResponseList = new ArrayList<>();

        //Comment -> CommentDTO.Response 로 다 변경해줘야 함.
        for (Comment comment : commentList) {
            commentResponseList.add(CommentDTO.Response.from(comment));
        }
        return commentResponseList;
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public void modify(CommentDTO.ModifyRequest request) {
        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow();
        comment.setContent(request.getContent());
        commentRepository.save(comment);
    }
}
