package com.example.finalproject.Controller;

import com.example.finalproject.domain.Experience;
import com.example.finalproject.domain.Project;
import com.example.finalproject.service.dto.ExperienceDTO;
import com.example.finalproject.service.dto.ProjectDTO;
import com.example.finalproject.service.impl.ProjectServiceImpl;
import com.example.finalproject.service.mapper.impl.ProjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectServiceImpl projectServiceImpl;

    private final ProjectMapper projectMapper;

    public ProjectController(ProjectServiceImpl projectServiceImpl, ProjectMapper projectMapper){
        this.projectMapper = projectMapper;
        this.projectServiceImpl = projectServiceImpl;
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false) String textSearch, Pageable pageable) {
        Page<ProjectDTO> projectDTOS = projectServiceImpl.findAll(pageable);
        model.addAttribute("projects",projectDTOS);
        return "project/project_index";
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable) {
        model.addAttribute("project", new ProjectDTO());
        return "project/project_create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("project/project_create");
            return modelAndView;
        }
        projectServiceImpl.save(projectDTO);
        ModelAndView modelAndView = new ModelAndView("project/project_index");
        modelAndView.addObject("experience", projectDTO);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model,Pageable pageable){
        Optional<ProjectDTO> projects = projectServiceImpl.findOne(id);
        if (projects!=null) {
            model.addAttribute("projects", projects);
            return "project/project_edit";
        } else {
            return "redirect:/projects/detail";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("projects") @Valid ProjectDTO projectDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "project/project_edit";
        }
        projectDTO.setId(id);
        projectServiceImpl.save(projectDTO);
        return "redirect:/project/detail";
    }
}
