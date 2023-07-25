package com.example.finalproject.Controller;

import com.example.finalproject.domain.Department;
import com.example.finalproject.domain.Skill;
import com.example.finalproject.repository.DepartmentRepository;
import com.example.finalproject.repository.SkillRepository;
import com.example.finalproject.service.DepartmentService;
import com.example.finalproject.service.SkillService;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.SkillDTO;
import com.example.finalproject.service.impl.SkillServiceImpl;
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
@RequestMapping("/skill")
public class SkillController {
    private final SkillServiceImpl skillServiceImpl;

    private final SkillRepository skillRepository;

    public SkillController(SkillServiceImpl skillServiceImpl, SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
        this.skillServiceImpl = skillServiceImpl;
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false) String textSearch, Pageable pageable) {
        Page<SkillDTO> skills = skillServiceImpl.findAll(pageable);
        model.addAttribute("department", skills);
        return "skill/index";
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable) {
        model.addAttribute("skill", new Skill());
        return "skill/create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("skill") @Valid SkillDTO skillDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("skill/create");
            return modelAndView;
        }
        skillServiceImpl.save(skillDTO);
        ModelAndView modelAndView = new ModelAndView("skill/index");
        modelAndView.addObject("skills", skillDTO);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model,Pageable pageable){
        Optional<SkillDTO> skills = skillServiceImpl.findOne(id);
        if (skills!=null) {
            model.addAttribute("department", skills);
            return "department/edit";
        } else {
            return "redirect:/skill/detail";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("skill") @Valid SkillDTO skillDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "skill/edit";
        }
        skillDTO.setId(id);
        skillServiceImpl.save(skillDTO);
        return "redirect:/department/detail";
    }
}
