package com.example.finalproject.controller;

import com.example.finalproject.security.AuthorizationService;
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

import java.util.*;

@Controller
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateService certificateService;
    private final EmployeeService employeeService;
    private final AuthorizationService authorizationService;

    public CertificateController(CertificateService certificateService, EmployeeService employeeService, AuthorizationService authorizationService) {
        this.employeeService = employeeService;
        this.certificateService = certificateService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/index")
    public String index(Model model, Pageable pageable, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        String username = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(username).orElseThrow(() -> new RuntimeException("Employee not found"));
        List<CertificateDTO> certificates = certificateService.findByEmployeeId(loggedInEmployee.getId());
        model.addAttribute("certificates", certificates);
        model.addAttribute("username", username);
        model.addAttribute("employee", loggedInEmployee);
        model.addAttribute("isAdmin", isAdmin);
        return "certificate/index";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        Optional<CertificateDTO> certificate = certificateService.findOne(id);
        model.addAttribute("certificate", certificate.orElse(null));
        return "certificate/detail";
    }

    @GetMapping("/add")
    public String showAdd(Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);

        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("employee", loggedInEmployee);
        model.addAttribute("certificateDTO", new CertificateDTO());
        model.addAttribute("isAdmin", isAdmin);
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
        if (certificateDTO.getExpirationDate() != null && certificateDTO.getIssueDate() != null) {
            if (certificateDTO.getExpirationDate().before(certificateDTO.getIssueDate())) {
                bindingResult.rejectValue("expirationDate", "expirationDate.before.issueDate", "Ngày hết hạn phải sau ngày cấp");
                return "certificate/add";
            }
        }
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        certificateDTO.setEmployee(employeeService.findOne(loggedInEmployee.getId()).get());
        certificateService.save(certificateDTO);
        return "redirect:/certificates/index";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
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
        if (certificateDTO.getExpirationDate() != null && certificateDTO.getIssueDate() != null) {
            if (certificateDTO.getExpirationDate().before(certificateDTO.getIssueDate())) {
                bindingResult.rejectValue("expirationDate", "expirationDate.before.issueDate", "Ngày hết hạn phải sau ngày cấp");
                return "certificate/add";
            }
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