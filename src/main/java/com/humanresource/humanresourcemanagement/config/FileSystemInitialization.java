package com.humanresource.humanresourcemanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FileSystemInitialization implements ApplicationRunner {
    private final FileStorageService fileStorageService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        fileStorageService.init();
    }
}
