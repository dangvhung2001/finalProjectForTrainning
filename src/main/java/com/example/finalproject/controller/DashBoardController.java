package com.example.finalproject.controller;

import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.impl.DashBoardServiceImpl;
import com.example.finalproject.service.impl.ProjectServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class DashBoardController {
    private final DashBoardServiceImpl dashBoardService;

    private final ProjectServiceImpl projectService;

    public DashBoardController(DashBoardServiceImpl dashBoardService, ProjectServiceImpl projectService) {
        this.dashBoardService = dashBoardService;
        this.projectService = projectService;
    }

    @GetMapping("")
    public String DashBoardController(Model model) {
        Long countEmployee = dashBoardService.getTotalEmployees();
        Long countDepartment = dashBoardService.getTotalDepartment();
        Long countProject = dashBoardService.getTotalProject();
        int countPm = projectService.getTotalPmCount();
        model.addAttribute("coutEmployee", countEmployee);
        model.addAttribute("countDepartment", countDepartment);
        model.addAttribute("countProject", countProject);
        model.addAttribute("countPm", countPm);
        return "dashboard/index";
    }

}
