package com.example.finalproject.controller;

import com.example.finalproject.service.CertificateService;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;
    private final EmployeeService employeeService;

    public CertificateController(CertificateService certificateService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.certificateService = certificateService;
    }

    @GetMapping("/index")
    public String index(Model model, @PageableDefault(size = 10) Pageable pageable, Authentication authentication) {
        String username = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(username).orElseThrow(() -> new RuntimeException("Employee not found"));
        Page<CertificateDTO> certificates = certificateService.findAll(pageable);
        model.addAttribute("certificates", certificates);
        model.addAttribute("username", username);
        model.addAttribute("employee", loggedInEmployee);
        return "certificate/index";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Optional<CertificateDTO> certificate = certificateService.findOne(id);
        model.addAttribute("certificate", certificate.orElse(null));
        return "certificate/detail";
    }

    @GetMapping("/add")
    public String showAdd( Model model, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("employee", loggedInEmployee);
        model.addAttribute("certificateDTO", new CertificateDTO());
        return "certificate/add";
    }

    @PostMapping("/add")
    public String doAdd(@ModelAttribute("certificateDTO") CertificateDTO certificateDTO,
                        BindingResult bindingResult,
                        Authentication authentication
    ) {
        if (bindingResult.hasErrors()) {
            return "certificate/add";
        }
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        certificateDTO.setEmployee(employeeService.findOne(loggedInEmployee.getId()).get());
        certificateService.save(certificateDTO);
        return "redirect:/certificates/index";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model) {
        Optional<CertificateDTO> certificate = certificateService.findOne(id);
        model.addAttribute("certificateDTO", certificate.orElse(null));
        return "certificate/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("certificateDTO") CertificateDTO certificateDTO,
                         BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "certificate/edit";
        }
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        certificateDTO.setEmployee(employeeService.findOne(loggedInEmployee.getId()).get());
        certificateDTO.setId(id);
        certificateService.save(certificateDTO);
        return "redirect:/certificates/{id}";
    }

    @PostMapping("/delete/{id}")
    public String doDelete(@PathVariable Long id) {
        certificateService.delete(id);
        return "redirect:/certificates/index";
    }
}
