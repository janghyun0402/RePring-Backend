package com.repring.be;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final S3UploadService s3UploadService;
    private final S3DownloadService s3DownloadService;


    public MusicDTO.Response save(MusicDTO.CreateRequest request) throws IOException {
        Music newMusic = Music.from(request);
        String objectKey = s3UploadService.upload(request.getCover(), "music");  //S3에 이미지 업로드
        newMusic.setImageUrl(objectKey);

        musicRepository.save(newMusic);         //DB에 저장
        return MusicDTO.Response.from(newMusic);
    }



    public List<Music> findAll(){
        return musicRepository.findAll();
    }
}
