package com.repring.be;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final MusicRepository musicRepository;

    private final S3DownloadService s3DownloadService;
    private final S3DeleteService s3DeleteService;

    @PostMapping(value = "/api/music", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String create(@RequestPart(value = "cover", required = false) MultipartFile cover,
                         @RequestPart(value = "title", required = false) String title,
                         @RequestPart(value = "artist", required = false) String artist) throws IOException {

        MusicDTO.CreateRequest request = MusicDTO.CreateRequest.of(title, artist, cover);
        musicService.save(request);
        return "ok";
    }


    /**
     * DB에 있는 id, title, artist 다 긁어서 리턴
     * @return
     */
    @GetMapping(value = "/api/music")
    public List<Music> readAll(){

        return musicRepository.findAll();
    }


    /**
     * 해당 id에 매핑되어 있는 사진 리턴
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/api/music/image/{id}")
    public ResponseEntity<byte[]> getCoverById(@PathVariable Long id) throws IOException {
        Music music = musicRepository.findById(id).orElseThrow();

        return s3DownloadService.downloadObject(music.getImageUrl());
    }

    @DeleteMapping(value = "/api/music/{id}")
    public String deleteMusic(@PathVariable Long id) throws IOException {

        Music music = musicRepository.findById(id).orElseThrow();
        s3DeleteService.deleteObject(music.getImageUrl());

        musicRepository.delete(music);
        return "Delete success!";
    }
}

