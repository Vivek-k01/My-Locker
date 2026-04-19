package com.example.demo.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Model.MyAppUser;
// import com.example.demo.Model.MyAppUser;
import com.example.demo.Model.MyAppUserRepository;
import com.example.demo.Model.UserDocument;
import com.example.demo.Model.UserDocumentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {
   
    @Autowired
    private UserDocumentRepository docRepo;
   
    @Autowired
    private MyAppUserRepository userRepo;
   
    // 1. Sabse pehle ye page dikhega (Landing Page)
    @GetMapping("/")
    public String welcome() {
        return "home"; 
    }

    // 2. Login page ka rasta
    @GetMapping("/req/login")
    public String login() {
        return "login";
    }

    // 3. Signup page ka rasta
    @GetMapping("/req/signup")
    public String signup() {
        return "signup";
    }

    // 4. Login hone ke baad user yahan aana chahiye
 @GetMapping("/dashboard")
public String showDashboard(Model model, java.security.Principal principal) {
    String username = principal.getName();
    MyAppUser user = userRepo.findByUsername(username);
    
    // Sirf is user ki files lao
    List<UserDocument> docs = docRepo.findByUser(user); 
    model.addAttribute("documents", docs);
    return "index";
}
    
  // 5. Password bhulne par
    @GetMapping("/req/forgotpassword")
    public String forgotpass() {
        return "forgotpassword";
    }

    @GetMapping("/req/newpassword")
    public String newpass() {
        return "newpassword";
    }

    // 6. About aur Contact pages (Inki HTML file templates mein bana lena)
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
@GetMapping("/profile")
public String showProfile(Model model, Principal principal) {
    if (principal != null) {
        String loginName = principal.getName();
        MyAppUser user = userRepo.findByUsername(loginName);
        
        if (user != null) {
            model.addAttribute("user", user);
            
            // QR Code ke liye data (Bina space error ke)
            String rawData = "ID:" + user.getId() + " | Name:" + user.getUsername() + " | Email:" + user.getEmail();
            try {
                String qrData = java.net.URLEncoder.encode(rawData, "UTF-8");
                model.addAttribute("qrData", qrData);
            } catch (Exception e) {
                model.addAttribute("qrData", "");
            }
        }
    }
    return "profile";
}


}