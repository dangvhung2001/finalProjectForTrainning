package com.example.finalproject.controller;

import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.dto.EmployeeDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller

public class AuthController {
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login/index";
    }

    @GetMapping("/changePassword")
    public String changePasswordForm() {
        return "login/change-password";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal,
                                 Model model) {
        String email = principal.getName();
        EmployeeDTO employee = employeeService.findByEmail(email).get();

        if (!passwordEncoder.matches(currentPassword, employee.getPassword())) {
            model.addAttribute("errorMessage", "Mật khẩu hiện tại không đúng");
            return "login/change-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Xác nhận mật khẩu mới không khớp");
            return "login/change-password";
        }

        employee.setPassword(passwordEncoder.encode(newPassword));
        employeeService.save(employee);

        model.addAttribute("successMessage", "Đổi mật khẩu thành công");
        return "login/change-password";
    }
}
