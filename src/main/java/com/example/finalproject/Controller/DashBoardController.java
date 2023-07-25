package com.example.finalproject.Controller;

import com.example.finalproject.service.dto.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home")
public class DashBoardController {
    @GetMapping("")
    public String DashBoardController(Model model) {
        return "dashboard/index";
    }
}
