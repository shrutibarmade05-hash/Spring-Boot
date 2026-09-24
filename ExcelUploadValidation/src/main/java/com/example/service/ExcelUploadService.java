package com.example.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Student;
import com.example.repository.StudentRepository;

@Service
public class ExcelUploadService {

    private final StudentRepository studentRepository;

    public ExcelUploadService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public UploadResponse processExcel(MultipartFile file) {

        List<RowError> errors = new ArrayList<>();

        int totalRows = 0;
        int insertedCount = 0;

        Set<String> excelEmails = new HashSet<>();
        Set<String> excelMobiles = new HashSet<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Check whether Excel has any data rows
            boolean hasData = false;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                for (Cell cell : row) {

                    if (cell != null &&
                        !getCellValue(cell).isBlank()) {

                        hasData = true;
                        break;
                    }
                }

                if (hasData) {
                    break;
                }
            }

            // If Excel contains no data rows
            if (!hasData) {
                throw new IllegalArgumentException("Excel file is empty");
            }

            // Process Excel rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                // Check if the complete row is blank
                boolean rowHasData = false;

                for (Cell cell : row) {

                    if (cell != null &&
                        !getCellValue(cell).isBlank()) {

                        rowHasData = true;
                        break;
                    }
                }

                if (!rowHasData) {
                    continue;
                }

                totalRows++;

                List<String> rowErrors = new ArrayList<>();

                String studentName = getCellValue(row.getCell(0));
                String email = getCellValue(row.getCell(1));
                String mobile = getCellValue(row.getCell(2));
                String course = getCellValue(row.getCell(3));
                String city = getCellValue(row.getCell(4));
                String feesValue = getCellValue(row.getCell(5));

                // Student name validation
                if (studentName.isBlank()) {

                    rowErrors.add("Student name is mandatory");

                } else if (studentName.length() < 3) {

                    rowErrors.add(
                        "Student name must contain at least 3 characters"
                    );
                }

                // Email validation
                if (email.isBlank()) {

                    rowErrors.add("Email is mandatory");

                } else if (!isValidEmail(email)) {

                    rowErrors.add("Invalid email format");

                } else {

                    if (studentRepository.existsByEmail(email)) {

                        rowErrors.add(
                            "Email already exists in database"
                        );
                    }

                    if (!excelEmails.add(email.toLowerCase())) {

                        rowErrors.add(
                            "Duplicate email in Excel file"
                        );
                    }
                }

                // Mobile validation
                if (mobile.isBlank()) {

                    rowErrors.add("Mobile is mandatory");

                } else if (!mobile.matches("\\d{10}")) {

                    rowErrors.add(
                        "Mobile number must contain exactly 10 digits"
                    );

                } else {

                    if (studentRepository.existsByMobile(mobile)) {

                        rowErrors.add(
                            "Mobile already exists in database"
                        );
                    }

                    if (!excelMobiles.add(mobile)) {

                        rowErrors.add(
                            "Duplicate mobile number in Excel file"
                        );
                    }
                }

                // Course validation
                if (!(course.equalsIgnoreCase("Java")
                        || course.equalsIgnoreCase("Python")
                        || course.equalsIgnoreCase("Testing")
                        || course.equalsIgnoreCase("Data Analytics"))) {

                    rowErrors.add("Invalid course");
                }

                // City validation
                if (city.isBlank()) {

                    rowErrors.add("City is mandatory");
                }

                // Fees validation
                BigDecimal fees = null;

                if (feesValue.isBlank()) {

                    rowErrors.add("Fees is mandatory");

                } else {

                    try {

                        fees = new BigDecimal(feesValue);

                        if (fees.compareTo(BigDecimal.ZERO) <= 0) {

                            rowErrors.add(
                                "Fees must be greater than 0"
                            );
                        }

                    } catch (NumberFormatException e) {

                        rowErrors.add("Fees must be numeric");
                    }
                }

                // If errors exist, skip this row
                if (!rowErrors.isEmpty()) {

                    RowError error = new RowError();

                    error.setRow(i + 1);
                    error.setEmail(email);
                    error.setMobile(mobile);
                    error.setErrors(rowErrors);

                    errors.add(error);

                    continue;
                }

                // Save valid student
                Student student = new Student();

                student.setStudentName(studentName);
                student.setEmail(email);
                student.setMobile(mobile);
                student.setCourse(course);
                student.setCity(city);
                student.setFees(fees);
                student.setCreatedAt(LocalDateTime.now());

                studentRepository.save(student);

                insertedCount++;
            }

        } catch (IllegalArgumentException e) {

            // Keep our custom empty Excel message
            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                "Unable to process Excel file"
            );
        }

        UploadResponse response = new UploadResponse();

        response.setMessage("Excel processing completed");
        response.setTotalRows(totalRows);
        response.setInsertedCount(insertedCount);
        response.setFailedCount(errors.size());
        response.setErrors(errors);

        return response;
    }

    private String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell).trim();
    }

    private boolean isValidEmail(String email) {

        String emailRegex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return Pattern.matches(emailRegex, email);
    }
}