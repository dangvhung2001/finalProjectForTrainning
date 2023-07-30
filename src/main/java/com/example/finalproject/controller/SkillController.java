package com.example.finalproject.controller;

import com.example.finalproject.repository.SkillRepository;
<<<<<<< HEAD
<<<<<<< HEAD:src/main/java/com/example/finalproject/Controller/SkillController.java
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.SkillService;
import com.example.finalproject.service.dto.EmployeeDTO;
=======
import com.example.finalproject.service.SkillService;
>>>>>>> 65f3340 (fix:fix bug addEmployee , feat: Login,changePassword Layout):src/main/java/com/example/finalproject/controller/SkillController.java
=======
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.SkillService;
import com.example.finalproject.service.dto.EmployeeDTO;
>>>>>>> dev
import com.example.finalproject.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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
    private final EmployeeService employeeService;

    public SkillController(SkillService skillService, SkillRepository skillRepository, EmployeeService employeeService) {
        this.skillRepository = skillRepository;
        this.skillService = skillService;
        this.employeeService = employeeService;
    }

    @GetMapping("/index")
    public String index(Model model, @RequestParam(required = false, defaultValue = "") String textSearch,
                        Pageable pageable,
                        Authentication authentication) {
        String username = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(username).orElseThrow(() -> new RuntimeException("Employee not found"));
        Page<SkillDTO> skills = skillService.findAll(textSearch, pageable);
        model.addAttribute("username", username);
        model.addAttribute("skills", skills);
        model.addAttribute("employee", loggedInEmployee);
        return "skill/index";
    }

    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id) {
        Optional<SkillDTO> skills = skillService.findOne(id);
        model.addAttribute("skills", skills.orElse(null));
        return "skill/detail";
    }

    @GetMapping("/add")
<<<<<<< HEAD
<<<<<<< HEAD:src/main/java/com/example/finalproject/Controller/SkillController.java
=======
>>>>>>> dev
    public String showAdd(Model model, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("employee", loggedInEmployee);
        model.addAttribute("skillDTO", new SkillDTO());
<<<<<<< HEAD
=======
    public String showAdd(Model model, Pageable pageable) {
        model.addAttribute("skill", new SkillDTO());
>>>>>>> 65f3340 (fix:fix bug addEmployee , feat: Login,changePassword Layout):src/main/java/com/example/finalproject/controller/SkillController.java
=======
>>>>>>> dev
        return "skill/add";
    }

    @PostMapping("/add")
    public String doAdd(@ModelAttribute("skill") @Valid SkillDTO skillDTO,
                        BindingResult bindingResult,
                        Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "skill/add";
        }
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        skillDTO.setEmployee(employeeService.findOne(loggedInEmployee.getId()).get());
        skillService.save(skillDTO);
        return "redirect:/skills/index";
    }

    @GetMapping("/edit/{id}")
<<<<<<< HEAD
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable) {
        Optional<SkillDTO> skills = skillService.findOne(id);
        if (skills != null) {
            model.addAttribute("department", skills);
            return "skill/edit";
        } else {
            return "redirect:/skill/{id}";
        }
=======
    public String showEdit(@PathVariable Long id, Model model) {
        Optional<SkillDTO> skills = skillService.findOne(id);
        model.addAttribute("certificateDTO", skills.orElse(null));
        return "skill/edit";
>>>>>>> dev
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("skill") @Valid SkillDTO skillDTO,
                         BindingResult bindingResult,
                         Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "skill/edit";
        }
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        skillDTO.setEmployee(employeeService.findOne(loggedInEmployee.getId()).get());
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
