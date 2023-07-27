package com.example.finalproject.controller;

import com.example.finalproject.repository.SkillRepository;
import com.example.finalproject.service.SkillService;
import com.example.finalproject.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/skills")
public class SkillController {
    private final SkillService skillService;

    private final SkillRepository skillRepository;

    public SkillController(SkillService skillService, SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
        this.skillService = skillService;
    }

    @GetMapping("/index")
    public String index(Model model, @RequestParam(required = false, defaultValue = "") String textSearch, Pageable pageable) {
        Page<SkillDTO> skills = skillService.findAll(textSearch,pageable);
        model.addAttribute("skills", skills);
        return "skill/index";
    }

    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id) {
        Optional<SkillDTO> skills = skillService.findOne(id);
        model.addAttribute("skills", skills.orElse(null));
        return "skill/detail";
    }

    @GetMapping("/add")
    public String showAdd(Model model, Pageable pageable) {
        model.addAttribute("skill", new SkillDTO());
        return "skill/add";
    }

    @PostMapping("/add")
    public String doAdd(@ModelAttribute("skill") @Valid SkillDTO skillDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "skill/add";
        }
        skillService.save(skillDTO);
        return "redirect:/skills/index";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable) {
        Optional<SkillDTO> skills = skillService.findOne(id);
        if (skills != null) {
            model.addAttribute("department", skills);
            return "skill/edit";
        } else {
            return "redirect:/skill/{id}";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("skill") @Valid SkillDTO skillDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "skill/edit";
        }
        skillDTO.setId(id);
        skillService.save(skillDTO);
        return "redirect:/skills/{id}";
    }

    @GetMapping("/delete/{id}")
    public String doDelete(@PathVariable Long id) {
        skillService.delete(id);
        return "redirect:/skills/index";
    }
}
