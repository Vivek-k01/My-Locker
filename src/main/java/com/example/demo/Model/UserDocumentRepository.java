package com.example.demo.Model;

    


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserDocumentRepository extends JpaRepository<UserDocument, Long> {
    
    // Sirf login kiye hue User ke hi saare documents nikal kar laana
    List<UserDocument> findByUser(MyAppUser user);
}