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
    private final ExperienceServiceImpll experienceServiceImpllImpll;

    public ExperienceController(ExperienceServiceImpll experienceImpll) {
        this.experienceServiceImpllImpll = experienceImpll;
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false) String textSearch, Pageable pageable) {
        Page<ExperienceDTO> experienceDTOS = experienceServiceImpllImpll.findAll(pageable);
        model.addAttribute("experiences", experienceDTOS);
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
        experienceServiceImpllImpll.save(experienceDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:experience/create");
        modelAndView.addObject("experiences", experienceDTO);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable) {
        Optional<ExperienceDTO> experiences = experienceServiceImpllImpll.findOne(id);
        if (experiences != null) {
            model.addAttribute("experiences", experiences);
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
}
