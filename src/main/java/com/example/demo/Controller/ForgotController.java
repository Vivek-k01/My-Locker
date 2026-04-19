package com.example.demo.Controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Model.MyAppUser;
import com.example.demo.Model.MyAppUserRepository;
import com.example.demo.service.MailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/req/forgot")
    public String openEmail(){
        return "forgotpassword";
    }

    // EMAIL CHECK + OTP SEND
    @PostMapping("/req/otp")
    public String sendOtp(@RequestParam("email") String email, HttpSession session){

        MyAppUser user = myAppUserRepository.findByEmail(email);

        // EMAIL NOT FOUND
        if(user == null){
            System.out.println("Email not found");
            return "forgotpassword";
        }

        // OTP GENERATE
        int otp = 100000 + new Random().nextInt(900000);

        boolean flag = mailService.sendEmail("OTP Verification","Your OTP is: "+otp,email);

        if(flag){
            session.setAttribute("myotp", otp);
            session.setAttribute("email", email);
            return "varify_otp";
        }

        return "forgotpassword";
    }

    // OTP VERIFY
    @PostMapping("/req/varify_otp")
    public String verifyopt(@RequestParam("otp") int otp, HttpSession session){

        Integer myotp = (Integer) session.getAttribute("myotp");

        if(myotp == null){
            return "forgotpassword";
        }

        if(myotp == otp){
            return "changepass";
        }

        return "varify_otp";
    }

    // CHANGE PASSWORD
    @PostMapping("/req/changepass")
    public String changepas(@RequestParam("newpassword") String newpassword,
                            HttpSession session){

        String email = (String)session.getAttribute("email");

        MyAppUser user = myAppUserRepository.findByEmail(email);

        if(user == null){
            return "forgotpassword";
        }

        user.setPassword(passwordEncoder.encode(newpassword));

        myAppUserRepository.save(user);

        session.removeAttribute("email");
        session.removeAttribute("myotp");

        return "login";
    }
}
