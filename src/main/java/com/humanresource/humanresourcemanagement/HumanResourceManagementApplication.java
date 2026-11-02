package com.humanresource.humanresourcemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import jakarta.servlet.MultipartConfigElement;
import java.io.File;

@SpringBootApplication
public class HumanResourceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumanResourceManagementApplication.class, args);
    }


}