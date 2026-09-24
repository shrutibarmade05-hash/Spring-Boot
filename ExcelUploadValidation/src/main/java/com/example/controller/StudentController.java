package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.ExcelUploadService;
import com.example.service.UploadResponse;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final ExcelUploadService excelUploadService;

    public StudentController(ExcelUploadService excelUploadService) {
        this.excelUploadService = excelUploadService;
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<?> uploadExcel(
            @RequestParam(value = "file", required = false) MultipartFile file) {

        // File missing or empty
        if (file == null || file.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("File is missing or empty");
        }

        // Only .xlsx file allowed
        String fileName = file.getOriginalFilename();

        if (fileName == null ||
                !fileName.toLowerCase().endsWith(".xlsx")) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Only .xlsx files are allowed");
        }

        try {

            UploadResponse response =
                    excelUploadService.processExcel(file);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to process Excel file");
        }
    }
}