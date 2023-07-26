package com.example.finalproject.Controller;

import com.example.finalproject.domain.Department;
import com.example.finalproject.repository.DepartmentRepository;
import com.example.finalproject.service.DepartmentService;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.impl.DepartmentServiceImpl;
import com.example.finalproject.service.mapper.impl.DepartmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final DepartmentMapper departmentMapper;

    private final DepartmentRepository departmentRepository;
    public DepartmentController(DepartmentServiceImpl departmentServiceImpl, DepartmentRepository departmentRepository,DepartmentMapper departmentMapper) {
        this.departmentServiceImpl = departmentServiceImpl;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false) String textSearch,Pageable pageable) {
        Page<DepartmentDTO> departments = departmentServiceImpl.findAll(pageable);
        model.addAttribute("departments", departments);
        return "department/department_index";
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable) {
        model.addAttribute("department", new DepartmentDTO());
        List<Department> department = departmentRepository.findByParentIsNull();
        model.addAttribute("department_parent", department);
        return "department/department_create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("department") @Valid DepartmentDTO departmentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView("department/department_create");
            return modelAndView;
        }
        departmentServiceImpl.save(departmentDTO);
        ModelAndView modelAndView = new ModelAndView("redirect:/department/detail");
        modelAndView.addObject("department", departmentDTO);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable){
        Optional<DepartmentDTO> departmentOptional = departmentServiceImpl.findOne(id);
        if (departmentOptional.isPresent()) {
            DepartmentDTO department = departmentOptional.get();
            model.addAttribute("department", department);
            return "department/department_edit";
        } else {
            return "redirect:/department/detail";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("department") @Valid DepartmentDTO departmentDTO,BindingResult bindingResult) {
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
