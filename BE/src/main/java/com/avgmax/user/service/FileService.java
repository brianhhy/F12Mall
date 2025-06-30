package com.avgmax.user.service;

import java.util.List;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avgmax.global.exception.ErrorCode;
import com.avgmax.global.service.MinioService;
import com.avgmax.user.exception.UserException;
@Service
@RequiredArgsConstructor
public class FileService {
    private final MinioService minioService;

    public List<String> uploadForUser(String userId, List<MultipartFile> files) {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String url = minioService.uploadFile(
                        userId + "/" + file.getOriginalFilename(),
                        file.getInputStream(),
                        file.getSize(),
                        file.getContentType());
                urls.add(url);
            } catch (Exception e) {
                throw UserException.of(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }

        return urls;
    }
}