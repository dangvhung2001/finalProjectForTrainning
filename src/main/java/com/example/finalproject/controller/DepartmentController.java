package com.example.finalproject.controller;

import com.example.finalproject.domain.Department;
import com.example.finalproject.repository.DepartmentRepository;
import com.example.finalproject.security.AuthorizationService;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.impl.DepartmentServiceImpl;
import com.example.finalproject.service.mapper.impl.DepartmentMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentServiceImpl departmentServiceImpl;
    private final DepartmentMapperImpl departmentMapper;
    private final DepartmentRepository departmentRepository;
    private final AuthorizationService authorizationService;

    public DepartmentController(DepartmentServiceImpl departmentServiceImpl,
                                DepartmentRepository departmentRepository,
                                DepartmentMapperImpl departmentMapper,
                                AuthorizationService authorizationService) {
        this.departmentServiceImpl = departmentServiceImpl;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false, defaultValue = "") String textSearch, Pageable pageable, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        Page<DepartmentDTO> departments = departmentServiceImpl.findAll(textSearch, pageable);
        model.addAttribute("departments", departments);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        return "department/index";
    }

    @GetMapping("/detail/{id}")
    public String detailProject(@PathVariable Long id, Model model, Authentication authentication) {
        Optional<DepartmentDTO> departmentDTO = departmentServiceImpl.findOne(id);
        if (departmentDTO.isPresent()) {
            boolean isAdmin = authorizationService.isAdmin(authentication);
            Long count = departmentServiceImpl.countByDepartmentId(id);
            List<EmployeeDTO> employeeList = departmentServiceImpl.findByDepartmentId(id);
            DepartmentDTO department = departmentDTO.get();
            model.addAttribute("count", count);
            authorizationService.addUsernameToModel(model, authentication);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("employeeList", employeeList);
            model.addAttribute("departments", department);
            return "department/detail";
        } else {
            return "redirect:/department/detail";
        }
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        model.addAttribute("department", new DepartmentDTO());
        List<Department> department = departmentRepository.findAll();
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("department_parent", department);
        return "department/create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("department") @Valid DepartmentDTO departmentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("department/create");
            List<Department> departments = departmentRepository.findAll();
            modelAndView.addObject("department_parent", departments);
            return modelAndView;
        }

        // Kiểm tra tên bộ phận đã tồn tại
        Optional<DepartmentDTO> existingDepartment = departmentServiceImpl.findByName(departmentDTO.getName());
        if (existingDepartment.isPresent()) {
            bindingResult.rejectValue("name", "error.department", "Tên bộ phận đã tồn tại");
            List<Department> departments = departmentRepository.findAll();
            ModelAndView modelAndView = new ModelAndView("department/create");
            modelAndView.addObject("department_parent", departments);
            return modelAndView;
        }

        // Kiểm tra mã bộ phận đã tồn tại
        Optional<DepartmentDTO> existingDepartmentCode = departmentServiceImpl.findByDepartmentCode(departmentDTO.getDepartmentCode());
        if (existingDepartmentCode.isPresent()) {
            bindingResult.rejectValue("departmentCode", "error.department", "Mã bộ phận đã tồn tại");
            ModelAndView modelAndView = new ModelAndView("department/create");
            List<Department> departments = departmentRepository.findAll();
            modelAndView.addObject("department_parent", departments);
            return modelAndView;
        }

        departmentServiceImpl.save(departmentDTO);
        return new ModelAndView("redirect:/department/detail");
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable, Authentication authentication) {
        Optional<DepartmentDTO> departmentOptional = departmentServiceImpl.findOne(id);
        if (departmentOptional.isPresent()) {
            boolean isAdmin = authorizationService.isAdmin(authentication);
            DepartmentDTO department = departmentOptional.get();
            authorizationService.addUsernameToModel(model, authentication);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("department", department);
            List<Department> departments = departmentRepository.findAll();
            model.addAttribute("department_parent", departments);
            return "department/edit";
        } else {
            return "redirect:/department/detail";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("department") @Valid DepartmentDTO departmentDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "department/edit";
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