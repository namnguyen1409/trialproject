package com.hsf302.trialproject.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public interface StorageService {
    void init();

    String store(MultipartFile file);

    String saveToTemp(MultipartFile file);

    String moveToUploads(String filename);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    Path loadTemp(String filename);

    Resource loadTempAsResource(String filename);

    boolean isValidSize(MultipartFile file, int maxSize);

    boolean hasValidExtension(MultipartFile file, String[] allowedExtensions);

    boolean isImage(MultipartFile file);

    void deleteFile(String filename);
}
