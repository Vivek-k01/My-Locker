package com.example.demo.Model;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Jab file size limit cross hogi, ye function chalega
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "File size is too big! Please upload less than 10MB.");
        return "redirect:/documents/upload"; // Wapas usi page par bhej do error ke saath
    }
}