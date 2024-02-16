package com.repring.be;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Music {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;

    private String imageUrl;

    public static Music from(MusicDTO.CreateRequest request) {      //RequestDto -> Music
        return Music.builder()
                .artist(request.getArtist())
                .title(request.getTitle())
                .build();
    }
    public static Music of(String title, String artist) {
        return Music.builder()
                .title(title)
                .artist(artist)
                .build();
    }
}
