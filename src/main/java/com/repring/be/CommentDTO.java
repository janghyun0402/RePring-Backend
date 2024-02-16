package com.repring.be;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommentDTO {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CreateRequest {
        private Long musicId;
        private String content;

        public static CreateRequest of(Long id, String comment) {
            return CreateRequest.builder()
                    .musicId(id)
                    .content(comment)
                    .build();
        }
    }


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ModifyRequest {
        private Long commentId;
        private String content;

        public static ModifyRequest of(Long id, String content) {
            return ModifyRequest.builder()
                    .commentId(id)
                    .content(content)
                    .build();
        }
    }


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response {
        private Long id;
        private String content;
        private String createdDate;
        private String modifiedDate;

        public static Response from(Comment comment) {
            return Response.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .createdDate(comment.getCreatedDate())
                    .modifiedDate(comment.getModifiedDate())
                    .build();
        }

    }

}
