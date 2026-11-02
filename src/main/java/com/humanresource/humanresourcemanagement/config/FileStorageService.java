package com.humanresource.humanresourcemanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path baseUploadPath;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        baseUploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public void init() {
        try {
            Files.createDirectories(this.baseUploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create base upload directory");
        }
    }


    public String storeFile(MultipartFile image, String folderName) throws IOException {
        folderName = folderName.replace("..", "");
        Path targetFolder = this.baseUploadPath.resolve(folderName).normalize();

        if(!targetFolder.startsWith(this.baseUploadPath)){
            throw new RuntimeException("Invalid folder path");
        }

        try {
            Files.createDirectories(targetFolder);
        } catch (IOException e) {
            throw new RuntimeException("Could not create targeted folder");
        }

        //to prevent file name replacing
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path targetLocation = targetFolder.resolve(fileName);

        Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return folderName + "/" + fileName;
    }

    public boolean deleteFile(String path) throws IOException {

        Path storedPath = Paths.get(baseUploadPath + "\\" + path);
        var absPath = storedPath.toAbsolutePath();
        var normalized = absPath.normalize();
        Files.delete(storedPath);

        return true;
    }
}