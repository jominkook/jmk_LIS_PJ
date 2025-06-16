//package com.yk.logistic.service.file;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.UUID;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import io.swagger.v3.oas.models.Paths;
//import jakarta.persistence.criteria.Path;
//
//public class FileService {
//	private final String uploadDir = "C:/uploads/";
//
//
//    public String saveFile(MultipartFile file) {
//        try {
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//            Path filePath = Paths.get(uploadDir + fileName);
//            Files.createDirectories(filePath.getParent());
//            Files.write(filePath, file.getBytes());
//            return "/uploads/" + fileName; // 반환할 경로
//        } catch (IOException e) {
//            throw new RuntimeException("파일 저장 실패", e);
//        }
//    }
//}
