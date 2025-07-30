package com.farm404.samyang.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUploadUtil {
    
    private static final String UPLOAD_DIR = "C:/dev/samyang/upload/";
    
    public String saveFile(MultipartFile file, String subDir) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        
        // 업로드 디렉토리 생성
        String uploadPath = UPLOAD_DIR + subDir;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // 파일명 생성 (UUID + 원본 파일명)
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex > 0) {
            extension = originalFilename.substring(lastDotIndex);
        }
        
        String savedFilename = UUID.randomUUID().toString() + extension;
        
        // 파일 저장
        Path filePath = Paths.get(uploadPath, savedFilename);
        Files.write(filePath, file.getBytes());
        
        // 웹에서 접근 가능한 경로 반환
        return "/upload/" + subDir + "/" + savedFilename;
    }
    
    public void deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }
        
        // /upload/로 시작하는 경우 실제 파일 경로로 변환
        if (filePath.startsWith("/upload/")) {
            filePath = filePath.replace("/upload/", UPLOAD_DIR);
        }
        
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
    
    public boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    
    public boolean isValidFileSize(MultipartFile file, long maxSizeInMB) {
        long maxSizeInBytes = maxSizeInMB * 1024 * 1024;
        return file.getSize() <= maxSizeInBytes;
    }
}