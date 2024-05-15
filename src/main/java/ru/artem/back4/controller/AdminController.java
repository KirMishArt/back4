package ru.artem.back4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public String index(Principal principal) {
        if (principal == null) {
            return "adminAuthentication";
        }
        return "adminPage";
    }
}

