package com.example.finalproject.Controller;

import com.example.finalproject.domain.Experience;
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
        model.addAttribute("experiences",experienceDTOS);
        return "doc/experience_index";
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable) {
        model.addAttribute("experience", new Experience());
        return "doc/experience_create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("experience") @Valid ExperienceDTO experienceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("doc/experience_create");
            return modelAndView;
        }
        experienceServiceImpllImpll.save(experienceDTO);
        ModelAndView modelAndView = new ModelAndView("doc/experience_index");
        modelAndView.addObject("experience", experienceDTO);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model,Pageable pageable){
        Optional<ExperienceDTO> experiences = experienceServiceImpllImpll.findOne(id);
        if (experiences!=null) {
            model.addAttribute("experience", experiences);
            return "doc/experiences_edit";
        } else {
            return "redirect:/experiences/detail";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("experiences") @Valid ExperienceDTO experienceDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "experiences/edit";
        }
        experienceDTO.setId(id);
        experienceServiceImpllImpll.save(experienceDTO);
        return "redirect:/experience/detail";
    }
}
