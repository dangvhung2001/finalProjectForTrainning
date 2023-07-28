package com.example.finalproject.controller;

import com.example.finalproject.domain.Department;
import com.example.finalproject.repository.DepartmentRepository;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.impl.DepartmentServiceImpl;
import com.example.finalproject.service.mapper.impl.DepartmentMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentServiceImpl departmentServiceImpl;
    private final DepartmentMapperImpl departmentMapper;

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentServiceImpl departmentServiceImpl, DepartmentRepository departmentRepository, DepartmentMapperImpl departmentMapper) {
        this.departmentServiceImpl = departmentServiceImpl;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false, defaultValue = "") String textSearch, Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        Page<DepartmentDTO> departments = departmentServiceImpl.findAll(textSearch, pageable);
        model.addAttribute("departments", departments);
        model.addAttribute("username", username);
        return "department/department_index";
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable) {
        model.addAttribute("department", new DepartmentDTO());
        List<Department> department = departmentRepository.findAll();
        model.addAttribute("department_parent", department);
        return "department/department_create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("department") @Valid DepartmentDTO departmentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("department/department_create");
            return modelAndView;
        }

        departmentServiceImpl.save(departmentDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/department/detail");
        modelAndView.addObject("department", departmentDTO);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable) {
        Optional<DepartmentDTO> departmentOptional = departmentServiceImpl.findOne(id);
        if (departmentOptional.isPresent()) {
            DepartmentDTO department = departmentOptional.get();
            model.addAttribute("department", department);
            List<Department> departments = departmentRepository.findAll();
            model.addAttribute("department_parent", departments);
            return "department/department_edit";
        } else {
            return "redirect:/department/detail";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("department") @Valid DepartmentDTO departmentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "department/department_edit";
        }
        departmentDTO.setId(id);
        departmentServiceImpl.save(departmentDTO);
        return "redirect:/department/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentServiceImpl.delete(id);
        return "redirect:/department/detail";
    }
}