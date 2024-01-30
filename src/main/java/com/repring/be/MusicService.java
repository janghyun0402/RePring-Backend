package com.repring.be;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;
    private final S3UploadService s3UploadService;

//    public Long save(Music music){
//        musicRepository.save(music);
//        return music.getId();
//    }

    public MusicDTO.Response save(MusicDTO.CreateRequest request) throws IOException {
        Music newMusic = Music.from(request);
        String imageUrl = s3UploadService.upload(request.getCover(), "music");
        newMusic.setImageUrl(imageUrl);

        musicRepository.save(newMusic);
        return MusicDTO.Response.from(newMusic);
    }
    public List<Music> findAll(){
        return musicRepository.findAll();
    }
}
