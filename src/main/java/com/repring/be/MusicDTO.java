package com.repring.be;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.multi.MultiLabelUI;

public class MusicDTO {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class CreateRequest {
        private String title;
        private String artist;
        private MultipartFile cover;

        public static CreateRequest of(String title, String artist, MultipartFile cover){
            return CreateRequest.builder()
                    .title(title)
                    .artist(artist)
                    .cover(cover)
                    .build();
        }
    }
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response {
        private Long id;
        private String title;
        private String artist;

        public static Response from(Music music) {
            return Response.builder()
                    .id(music.getId())
                    .title(music.getTitle())
                    .artist(music.getArtist())
                    .build();
        }
    }
}
