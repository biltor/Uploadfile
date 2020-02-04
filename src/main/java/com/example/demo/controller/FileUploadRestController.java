package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class FileUploadRestController {
    private static final Logger logger = Logger.getLogger(FileUploadRestController.class.getName());
    @Value("${upload.path}")
    private String path;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RuntimeException("You must select the a file for uploading");
        }
        InputStream inputStream = file.getInputStream();

        String originalName = file.getOriginalFilename();
        String name = file.getName();
        String contentType = file.getContentType();
        long size = file.getSize();
        logger.info("inputStream: " + inputStream);
        logger.info("originalName: " + originalName);
        logger.info("name: " + name);
        logger.info("contentType: " + contentType);
        logger.info("size: " + size);
        try {
           // var fileName = file.getOriginalFilename();


            Files.copy(inputStream, Paths.get(path + originalName), StandardCopyOption.REPLACE_EXISTING);


        }
        catch (Exception e) {
            String msg = String.format("Failed to store file", file.getName());
            e.printStackTrace();
        }
        return new ResponseEntity<String>(originalName, HttpStatus.OK);
    }
}


