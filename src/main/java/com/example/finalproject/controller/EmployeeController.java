package com.example.finalproject.controller;

import com.example.finalproject.domain.Role;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.service.CertificateService;
import com.example.finalproject.service.DepartmentService;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.SkillService;
import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.dto.SkillDTO;
import com.itextpdf.text.Document;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
    private final TemplateEngine templateEngine;
    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService,
                              RoleRepository roleRepository,
                              EmployeeRepository employeeRepository,
                              SkillService skillService,
                              CertificateService certificateService,
                              TemplateEngine templateEngine) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.skillService = skillService;
        this.certificateService = certificateService;
        this.templateEngine = templateEngine;
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
    @GetMapping("/profile/export-pdf/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> exportProfileToPdf(@PathVariable Long id) {
        // Lấy thông tin của employee từ service dựa vào id
        EmployeeDTO employeeDTO = employeeService.findOne(id).orElse(null);

        if (employeeDTO == null) {
            return ResponseEntity.notFound().build();
        }

        List<SkillDTO> skills = skillService.findByEmployeeId(id);
        List<CertificateDTO> certificates = certificateService.findByEmployeeId(id);

        // Tạo context để truyền dữ liệu vào template Thymeleaf
        Context context = new Context();
        context.setVariable("employees", employeeDTO);
        // Để xuất danh sách kỹ năng và chứng chỉ, bạn cần lấy dữ liệu từ service và truyền vào context
        context.setVariable("skills", skills);
        context.setVariable("certificates", certificates);

        // Render template Thymeleaf thành HTML
        String htmlContent = templateEngine.process("employees/pdf_export_template", context);

        // Chuyển đổi HTML thành file PDF sử dụng thư viện ITextRenderer
        try {
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);

            // Thêm cài đặt để hỗ trợ các link trong HTML
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("static/fonts/font-awesome-4.7.0/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.layout();
            renderer.createPDF(pdfOutputStream);

            // Set the appropriate headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "profile.pdf"); // Set the file name for download

            // Return the byte array and headers as a ResponseEntity
            return new ResponseEntity<>(pdfOutputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
