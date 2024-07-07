package com.witcher.e_commerce.application.witcher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/admin-dashboard")
    public String getAdmin(){
        return "admin-dashboard";
    }
    @GetMapping("/categories")
    public String getCategories(){
        return "categories";
    }
}
