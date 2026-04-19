package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;

import com.example.demo.Model.FileStorageService;
import com.example.demo.Model.MyAppUser;
import com.example.demo.Model.MyAppUserRepository;
import com.example.demo.Model.UserDocument;
import com.example.demo.Model.UserDocumentRepository;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired private FileStorageService fileService;
    @Autowired private UserDocumentRepository docRepo;
    @Autowired private MyAppUserRepository userRepo;

    @PostMapping("/upload")
public String uploadDocument(@RequestParam("file") MultipartFile file,
                             @RequestParam("displayName") String displayName,
                             Principal principal) {
    try {
        if (principal == null) return "redirect:/req/login";

        String username = principal.getName();
        MyAppUser currentUser = userRepo.findByUsername(username);

        // CHECK: Agar user null hai toh save nahi hoga
        if (currentUser == null) {
            System.out.println("ERROR: User nahi mila!");
            return "redirect:/dashboard?error=usernotfound";
        }

        if (!file.isEmpty()) {
            String fileName = fileService.saveFile(file);
            
            UserDocument doc = new UserDocument();
            doc.setDisplayName(displayName);
            doc.setOriginalFileName(file.getOriginalFilename());
            doc.setFilePath(fileName);
            
            // SABSE ZAROORI LINE: Ye database mein user_id bharta hai
            doc.setUser(currentUser); 
            
            doc.setUploadTime(java.time.LocalDateTime.now());
            doc.setFileSize((file.getSize() / 1024) + " KB");
            
            // Yahan error aa rahi hai MySQL ki
            docRepo.save(doc);
            System.out.println("SUCCESS: Database mein entry ho gayi!");
        }
        return "redirect:/dashboard?success";
    } catch (Exception e) {
        // Bhai yahan console mein error print hogi, use check karo
        e.printStackTrace(); 
        return "redirect:/dashboard?error";
    }
}

    // Naye external folder se file ka rasta nikaalo
    private Path resolveFilePath(String storedFileName) {
        return fileService.getRootPath().resolve(storedFileName);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable Long id) {
        try {
            UserDocument doc = docRepo.findById(id).orElseThrow();
            Path path = resolveFilePath(doc.getFilePath());
            Resource resource = new UrlResource(path.toUri());

            String contentType = Files.probeContentType(path);
            if (contentType == null) contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getOriginalFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        try {
            UserDocument doc = docRepo.findById(id).orElseThrow();
            Path path = resolveFilePath(doc.getFilePath());
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getOriginalFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id) {
        try {
            UserDocument doc = docRepo.findById(id).orElseThrow();
            Path path = resolveFilePath(doc.getFilePath());
            Files.deleteIfExists(path);
            docRepo.deleteById(id);
            return "redirect:/dashboard?deleted";
        } catch (Exception e) {
            return "redirect:/dashboard?error";
        }
    }
}