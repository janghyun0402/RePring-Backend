package com.repring.be;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CSVOutput;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException{
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        return uploadFile(uploadFile, dirName);
    }


    public String uploadFile(File uploadFile, String dirName) throws IOException {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();     //S3에 저장된 object key

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)     //파일 업로드
                .withCannedAcl(CannedAccessControlList.PublicRead));

        removeNewFile(uploadFile);
        return fileName;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    /**
     * MultipartFile을 File로 바꾸는 메서드
     * @param file
     * @return
     * @throws IOException
     */
    public Optional<File> convert(MultipartFile file) throws IOException {

        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
