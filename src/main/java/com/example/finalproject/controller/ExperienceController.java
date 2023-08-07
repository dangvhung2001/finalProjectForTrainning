package com.example.finalproject.controller;

import com.example.finalproject.security.AuthorizationService;
import com.example.finalproject.service.dto.ExperienceDTO;
import com.example.finalproject.service.impl.ExperienceServiceImpll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("experience")
public class ExperienceController {
    private final ExperienceServiceImpll experienceServiceImpllImpll;
    private final AuthorizationService authorizationService;

    public ExperienceController(ExperienceServiceImpll experienceImpll, AuthorizationService authorizationService) {
        this.experienceServiceImpllImpll = experienceImpll;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false) String textSearch, Pageable pageable, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        Page<ExperienceDTO> experienceDTOS = experienceServiceImpllImpll.findAll(pageable);
        model.addAttribute("experienceDTOS", experienceDTOS);
        return "experience/index";
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("experience", new ExperienceDTO());
        return "experience/create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("experience") @Valid ExperienceDTO experienceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("experience/create");
            return modelAndView;
        }
        Optional<ExperienceDTO> existingName = experienceServiceImpllImpll.findByName(experienceDTO.getName_experience());
        if (existingName.isPresent()) {
            bindingResult.rejectValue("name_experience", "error.experience", "Tên kinh nghiệm đã tồn tại");
            ModelAndView modelAndView = new ModelAndView("redirect:/experience/detail");
            return modelAndView;
        }
        experienceServiceImpllImpll.save(experienceDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/experience/detail");
        modelAndView.addObject("experiences", experienceDTO);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable, Authentication authentication) {
        Optional<ExperienceDTO> experiences = experienceServiceImpllImpll.findOne(id);
        if (experiences.isPresent()) {
            boolean isAdmin = authorizationService.isAdmin(authentication);
            authorizationService.addUsernameToModel(model, authentication);
            model.addAttribute("isAdmin", isAdmin);
            ExperienceDTO experienceDTO = experiences.get();
            model.addAttribute("experiences", experienceDTO);
            return "experience/edit";
        } else {
            return "redirect:/experiences/detail";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("experiences") @Valid ExperienceDTO experienceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "experience/edit";
        }
        experienceDTO.setId(id);
        experienceServiceImpllImpll.save(experienceDTO);
        return "redirect:/experience/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        experienceServiceImpllImpll.delete(id);
        return "redirect:/experience/detail";
    }
}
