package com.example.finalproject.controller;

import com.example.finalproject.domain.Role;
import com.example.finalproject.domain.Skill;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.repository.SkillRepository;
import com.example.finalproject.service.CertificateService;
import com.example.finalproject.service.DepartmentService;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.SkillService;
import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.dto.SkillDTO;
import com.example.finalproject.service.impl.MailSenderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final RoleRepository roleRepository;
    private final SkillService skillService;
    private final CertificateService certificateService;
    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService,
                              RoleRepository roleRepository,
                              EmployeeRepository employeeRepository,
                              SkillService skillService,
                              CertificateService certificateService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.skillService = skillService;
        this.certificateService = certificateService;
    }

    @GetMapping("/index")
    public String index(@RequestParam(required = false, defaultValue = "") String textSearch, Pageable pageable, Model model, Authentication authentication) {
        String username = authentication.getName();
        Page<EmployeeDTO> listOfEmployees = employeeService.findAll(textSearch, pageable);
        model.addAttribute("listOfEmployees", listOfEmployees);
        model.addAttribute("username", username);
        return "employees/index";
    }

    @GetMapping("/search")
    public String listSearch(@RequestParam(required = false, defaultValue = "") String textSearch, @PageableDefault(size = 5) Pageable pageable, Model model) {
        Page<EmployeeDTO> employees = employeeService.findAll(textSearch, pageable);
        model.addAttribute("employees", employees);
        return "employees/search";
    }

    @GetMapping("/{id}")
    public String detailEmployee(@PathVariable Long id, Model model) {
        Optional<EmployeeDTO> employees = employeeService.findOne(id);
        model.addAttribute("employees", employees.orElse(null));
        return "employees/detail";
    }
    @GetMapping("/detail")
    public String detailEmployee(Model model, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        List<SkillDTO> skills = skillService.findByEmployeeId(loggedInEmployee.getId());
        List<CertificateDTO> certificates = certificateService.findByEmployeeId(loggedInEmployee.getId());
        model.addAttribute("employees", loggedInEmployee);
        model.addAttribute("skills", skills);
        model.addAttribute("certificates", certificates);
        return "employees/detail";
    }

    @GetMapping("/add")
    public String showAdd(Model model,@ModelAttribute("email") String email) {
        model.addAttribute("employee", new EmployeeDTO());
        List<Role> roles = roleRepository.findAll();
        List<DepartmentDTO> departments = departmentService.getAll();
        List<EmployeeDTO> listOfEmployees = employeeService.getAll();
        model.addAttribute("roles", roles);
        model.addAttribute("departments", departments);
        model.addAttribute("listOfEmployees", listOfEmployees);
//        model.addAttribute("email", email);
        return "employees/add";
    }

    @PostMapping("/add")
    public String doAdd(@ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
                        BindingResult bindingResult, RedirectAttributes redirectAttributes
                        )  {
        if (bindingResult.hasErrors()) {
            return "employees/add";
        }
        Optional<EmployeeDTO> existingEmployee = employeeService.findByEmail(employeeDTO.getEmail());
        if (existingEmployee.isPresent()) {
            bindingResult.rejectValue("email", "error.employee", "Email đã tồn tại");
//            redirectAttributes.addFlashAttribute("email", "Email đã tồn tại");
            return "redirect:employees/add";
        }
        Optional<EmployeeDTO> existingEmployeeCode = employeeService.findByEmployeeCode(employeeDTO.getEmployeeCode());
        if (existingEmployeeCode.isPresent()) {
            bindingResult.rejectValue("employeeCode", "error.employee", "Code đã tồn tại");
            return "employees/add";
        }
        employeeService.save(employeeDTO);
        return "redirect:/employees/index";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Authentication authentication) {
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        if (!id.equals(loggedInEmployee.getId())) {
            return "login/403";
        }
        Optional<EmployeeDTO> employee = employeeService.findOne(id);
        if (employee.isPresent()) {
            EmployeeDTO employeeDTO = employee.get();
            List<DepartmentDTO> departments = departmentService.getAll();
            List<Role> roles = roleRepository.findAll();
            List<EmployeeDTO> listOfEmployees = employeeService.getAll();
            model.addAttribute("roles", roles);
            model.addAttribute("employee", employeeDTO);
            model.addAttribute("departments", departments);
            model.addAttribute("listOfEmployees", listOfEmployees);
            return "employees/edit";
        } else {
            return "redirect:/employees/index";
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
                         @RequestParam("imageFile") MultipartFile imageFile,
                         Model model,
                         BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "employees/edit";
        }

        if (!imageFile.isEmpty()) {
            EmployeeDTO oldEmployee = employeeService.findOne(id).orElse(null);
            if (oldEmployee != null && oldEmployee.getImgUrl() != null && !oldEmployee.getImgUrl().isEmpty()) {
                File oldImage = new File(oldEmployee.getImgUrl());
                if (oldImage.exists()) {
                    oldImage.delete();
                }
            }
            String uploadDir = "D:\\ThucTap\\FinalProject\\src\\main\\resources\\static\\image\\";
            String fileName = imageFile.getOriginalFilename();
            File uploadPath = new File(uploadDir);
            File targetFile = new File(uploadPath, fileName);
            imageFile.transferTo(targetFile);
            employeeDTO.setImgUrl(fileName);
        }
        employeeService.updateEmployee(employeeDTO, imageFile);
        return "redirect:/employees/index";
    }

    @GetMapping("/delete/{id}")
    public String doDelete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employees/index";
    }

//    @GetMapping("/employees/profile/{id}/export-pdf")
//    @ResponseBody
//    public byte[] exportProfileToPdf(@PathVariable Long id) {
//        EmployeeDTO employeeDTO = employeeService.findOne(id).orElse(null);
//
//        if (employeeDTO == null) {
//            throw new RuntimeException("Employee not found");
//            return null;
//        }
//        String htmlContent = readProfileHtml();
//
//        // Load profile.html từ thư mục resources/templates
//        htmlContent = htmlContent.replace("{{employeeName}}", employeeDTO.getFirstname() + " " + employeeDTO.getLastname());
//        htmlContent = htmlContent.replace("{{employeeEmail}}", employeeDTO.getEmail());
//        // Thay thế các trường thông tin trong profile.html với thông tin của employeeDTO
//        // Ví dụ: htmlContent = htmlContent.replace("{{employeeName}}", employeeDTO.getFirstname() + " " + employeeDTO.getLastname());
//
//        // Chuyển đổi profile.html thành file PDF
//        try {
//            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
//            ITextRenderer renderer = new ITextRenderer();
//            renderer.setDocumentFromString(htmlContent);
//            renderer.layout();
//            renderer.createPDF(pdfOutputStream);
//
//            // Trả về byte[] của file PDF để người dùng tải xuống
//            return pdfOutputStream.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Xử lý nếu có lỗi khi tạo PDF
//            // Ví dụ: throw new RuntimeException("Failed to export profile to PDF");
//            return null;
//        }
//    }
    private String readProfileHtml() {
        try {
            // Thay đổi đường dẫn nếu cần
            Path path = Paths.get("src/main/resources/templates/employees/detail.html");
            byte[] fileBytes = Files.readAllBytes(path);
            return new String(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

