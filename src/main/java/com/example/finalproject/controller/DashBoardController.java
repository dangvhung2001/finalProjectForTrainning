package com.example.finalproject.controller;

import com.example.finalproject.security.AuthorizationService;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.SkillDTO;
import com.example.finalproject.service.impl.DashBoardServiceImpl;
import com.example.finalproject.service.impl.ProjectServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class DashBoardController {
    private final DashBoardServiceImpl dashBoardService;

    private final ProjectServiceImpl projectService;
    private final AuthorizationService authorizationService;

    public DashBoardController(DashBoardServiceImpl dashBoardService, ProjectServiceImpl projectService, AuthorizationService authorizationService) {
        this.dashBoardService = dashBoardService;
        this.projectService = projectService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("")
    public String DashBoardController(Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        Long countEmployee = dashBoardService.getTotalEmployees();
        Long countDepartment = dashBoardService.getTotalDepartment();
        Long countProject = dashBoardService.getTotalProject();
        int countPm = projectService.getTotalPmCount();
        model.addAttribute("coutEmployee", countEmployee);
        model.addAttribute("countDepartment", countDepartment);
        model.addAttribute("countProject", countProject);
        model.addAttribute("countPm", countPm);
        List<SkillDTO> languageData = dashBoardService.getLanguageUsed();
        model.addAttribute("languageData", languageData);
        return "dashboard/index";
    }

}
