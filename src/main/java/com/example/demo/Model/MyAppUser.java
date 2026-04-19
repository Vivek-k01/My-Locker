package com.example.demo.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "my_app_users") // Table name dena achhi practice hai
public class MyAppUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // FIX: AUTO se IDENTITY kar diya
    private Long id;

    private String username;
    private String email;
    private String password;

    @Column(name = "verification_token") // Spelling FIX: verfication -> verification
    private String verificationToken;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = true; 

    @Column(name = "reset_token")
    private String resetToken;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String verificationToken) { this.verificationToken = verificationToken; }
    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean isVerified) { this.isVerified = isVerified; }
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
}