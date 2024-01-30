package com.repring.be;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final S3UploadService s3UploadService;

//    @PostMapping("/api/music")
//    public String create(@RequestBody MusicDTO musicDTO){     //jackson이 JSON으로 된 Body를 자바 객체로 변환해줌
//
//        Music music = new Music();
//
//        music.setTitle(musicDTO.getTitle());
//        music.setArtist(musicDTO.getArtist());
//
//        int musicNum = musicService.save(music);
//        return musicNum + "번 음악 등록 완료";
//
//    }

    @PostMapping(value = "/api/music", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String create(@RequestPart(value = "cover", required = false) MultipartFile cover,
                         @RequestPart(value = "title", required = false) String title,
                         @RequestPart(value = "artist", required = false) String artist) throws IOException {

        MusicDTO.CreateRequest request = MusicDTO.CreateRequest.of(title, artist, cover);
        musicService.save(request);
        return "ok";
    }

    @PostMapping(value = "/api/musics")
    public MusicDTO.Response createMusic(@RequestPart(value = "cover", required = false) MultipartFile file,
                            @RequestPart(value = "music") MusicDTO.CreateRequest request) throws IOException{
        s3UploadService.upload(file, "");

        return musicService.save(request);
    }



    @GetMapping("/api/music")
    public String readAll(){
        return musicService.findAll().toString();
    }
}
