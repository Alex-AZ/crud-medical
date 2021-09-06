package com.example.patient.controllers;

import com.example.patient.fr.entities.PatientEntity;
import com.example.patient.fr.entities.VilleEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class LoginController {

    @GetMapping("/login")
    public String loginGet() {
        return "login/login_form";
    }

    @PostMapping("/login")
    public String loginPost() {
        return "redirect:/dashboard";
    }
}
