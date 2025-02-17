package com.hsf302.trialproject.common.controller;

import com.hsf302.trialproject.common.enums.MessageKeyEnum;
import com.hsf302.trialproject.common.service.MessageService;
import com.hsf302.trialproject.common.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileUploadController {
    private final StorageService storageService;
    private final MessageService messageService;

    @Value("${file.upload-avatar-size}")
    private int avatarSize;


    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String SUCCESS = "success";


    @PostMapping("/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        int maxAvatarSize = avatarSize * 1024 * 1024;
        if (!storageService.isValidSize(file, maxAvatarSize)) {
            response.put(STATUS, ERROR);
            response.put(MESSAGE, messageService.getMessage(MessageKeyEnum.ERROR_FILE_SIZE_EXCEEDS_LIMIT.getKey(), avatarSize));
            return ResponseEntity.badRequest().body(response);
        }
        if (!storageService.isImage(file)) {
            response.put(STATUS, ERROR);
            response.put(MESSAGE, messageService.getMessage(MessageKeyEnum.ERROR_FILE_IS_NOT_IMAGE.getKey()));
            return ResponseEntity.badRequest().body(response);
        } else {
            var url = storageService.store(file);
            response.put(STATUS, SUCCESS);
            response.put(MESSAGE, messageService.getMessage(MessageKeyEnum.SUCCESS_FILE_UPLOADED.getKey()));
            response.put("url", url);
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("/product")
    public ResponseEntity<Map<String, String>> uploadProductImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        if (!storageService.isImage(file)) {
            response.put(STATUS, ERROR);
            response.put(MESSAGE, messageService.getMessage(MessageKeyEnum.ERROR_FILE_IS_NOT_IMAGE.getKey()));
            return ResponseEntity.badRequest().body(response);
        } else {
            var url = storageService.saveToTemp(file);
            response.put(STATUS, SUCCESS);
            response.put(MESSAGE, messageService.getMessage(MessageKeyEnum.SUCCESS_FILE_UPLOADED.getKey()));
            response.put("url", url);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        var file = storageService.loadAsResource(filename);
        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/temps/{filename:.+}")
    public ResponseEntity<Resource> serveTempFile(@PathVariable String filename) {
        var file = storageService.loadTempAsResource(filename);
        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }




}
