package com.repring.be;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/music/comments/{id}")
    public String createComment(@PathVariable Long id, @RequestBody String content) {
        CommentDTO.CreateRequest request = CommentDTO.CreateRequest.of(id, content);
        commentService.create(request);

        return "ok";
    }

    @GetMapping("/api/music/comments/{id}")
    public List<CommentDTO.Response> readAllComments(@PathVariable Long id) {

        return commentService.readAll(id);
    }

    @DeleteMapping("/api/music/comments/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.delete(id);

        return "ok";
    }

    @PutMapping("/api/music/comments/{id}")
    public String modifyComment(@PathVariable Long id, @RequestBody String content) {
        CommentDTO.ModifyRequest request = CommentDTO.ModifyRequest.of(id, content);
        commentService.modify(request);

        return "ok";
    }
}
