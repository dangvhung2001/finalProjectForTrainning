package com.example.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class DashBoardController {
    @GetMapping("")
    public String DashBoardController(Model model) {
        return "dashboard/index";
    }
}
