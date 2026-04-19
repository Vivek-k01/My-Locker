package com.example.demo.Model;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

    // 1. Bhai ye hai tera naya aur pakka rasta (Project se bahar)
    private final Path root = Paths.get("D:/Interview/My-locker-storage/documents");

    // 2. Ye method apne aap folder bana dega agar nahi bana hoga
    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
                System.out.println("Bhai, External Folder Ready Hai: " + root.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload! " + e.getMessage());
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        // 3. Unique file name banao taaki duplicate na ho
        // Spaces ko "_" se replace kar rahe hain taaki URL mein %20 ka panga na ho
        String originalName = file.getOriginalFilename().replaceAll(" ", "_");
        String fileName = UUID.randomUUID().toString() + "_" + originalName;

        // 4. File ko naye path par copy karo
        Files.copy(file.getInputStream(), this.root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        // 5. Database mein sirf FileName return karo (Path hum resolveFilePath mein handle karenge)
        return fileName;
    }

    // Iska use Controller mein file dhoondne ke liye hoga
    public Path getRootPath() {
        return root;
    }
}