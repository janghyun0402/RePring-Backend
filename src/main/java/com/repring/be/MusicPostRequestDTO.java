package com.repring.be;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class MusicPostRequestDTO {
    private String title;
    private String artist;
    private Object cover;
}
