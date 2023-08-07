package com.example.finalproject.controller;

import com.example.finalproject.service.dto.ExperienceDTO;
import com.example.finalproject.service.impl.ExperienceServiceImpll;
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
@RequestMapping("experience")
public class ExperienceController {
    private final ExperienceServiceImpll experienceServiceImpl;

    public ExperienceController(ExperienceServiceImpll experienceImpll) {
        this.experienceServiceImpl = experienceImpll;
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false) String textSearch, Pageable pageable) {
        Page<ExperienceDTO> experienceDTOS = experienceServiceImpl.findAll(pageable);
        model.addAttribute("experienceDTOS", experienceDTOS);
        return "experience/index";
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable) {
        model.addAttribute("experience", new ExperienceDTO());
        return "experience/create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("experience") @Valid ExperienceDTO experienceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("experience/create");
            return modelAndView;
        }
        Optional<ExperienceDTO> existingName = experienceServiceImpl.findByName(experienceDTO.getName_experience());
        if (existingName.isPresent()) {
            bindingResult.rejectValue("name_experience", "error.experience", "Tên kinh nghiệm đã tồn tại");
            ModelAndView modelAndView = new ModelAndView("redirect:/experience/detail");
            return modelAndView;
        }
        experienceServiceImpl.save(experienceDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/experience/detail");
        modelAndView.addObject("experiences", experienceDTO);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable) {
        Optional<ExperienceDTO> experiences = experienceServiceImpl.findOne(id);
        if (experiences.isPresent()) {
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
        experienceServiceImpl.save(experienceDTO);
        return "redirect:/experience/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        experienceServiceImpl.delete(id);
        return "redirect:/experience/detail";
    }
}
