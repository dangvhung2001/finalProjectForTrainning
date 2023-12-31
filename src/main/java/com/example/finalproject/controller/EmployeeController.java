package com.example.finalproject.controller;

import com.example.finalproject.domain.Role;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.security.AuthorizationService;
import com.example.finalproject.service.CertificateService;
import com.example.finalproject.service.DepartmentService;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.SkillService;
import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.dto.SkillDTO;
import com.lowagie.text.pdf.BaseFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.security.Principal;
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
    private final AuthorizationService authorizationService;

    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService,
                              RoleRepository roleRepository,
                              EmployeeRepository employeeRepository,
                              SkillService skillService,
                              CertificateService certificateService,
                              TemplateEngine templateEngine,
                              AuthorizationService authorizationService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.skillService = skillService;
        this.certificateService = certificateService;
        this.templateEngine = templateEngine;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/index")
    public String index(@RequestParam(required = false, defaultValue = "") String textSearch, Pageable pageable, Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        Page<EmployeeDTO> listOfEmployees = employeeService.findAll(textSearch, pageable);
        model.addAttribute("listOfEmployees", listOfEmployees);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        return "employees/index";
    }

    @GetMapping("/search")
    public String listSearch(@RequestParam(required = false, defaultValue = "") String textSearch, Pageable pageable, Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        authorizationService.addUsernameToModel(model, authentication);
        Page<EmployeeDTO> employees = employeeService.findAll(textSearch, pageable);
        model.addAttribute("employee", employees);
        model.addAttribute("isAdmin", isAdmin);
        return "employees/search";
    }

    @GetMapping("/{id}")
    public String detailEmployee(@PathVariable Long id, Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        Optional<EmployeeDTO> employees = employeeService.findOne(id);
        model.addAttribute("employees", employees.orElse(null));
        return "employees/detail";
    }

    @GetMapping("/detail")
    public String detailEmployee(Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);

        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        List<SkillDTO> skills = skillService.findByEmployeeId(loggedInEmployee.getId());
        List<CertificateDTO> certificates = certificateService.findByEmployeeId(loggedInEmployee.getId());
        model.addAttribute("employees", loggedInEmployee);
        model.addAttribute("skills", skills);
        model.addAttribute("certificates", certificates);
        model.addAttribute("isAdmin", isAdmin);
        return "employees/detail";
    }

    @GetMapping("/add")
    public String showAdd(Model model, @ModelAttribute("email") String email, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        authorizationService.addUsernameToModel(model, authentication);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("employee", new EmployeeDTO());
        List<Role> roles = roleRepository.findAll();
        List<DepartmentDTO> departments = departmentService.getAll();
        List<EmployeeDTO> listOfEmployees = employeeService.getAll();
        model.addAttribute("roles", roles);
        model.addAttribute("departments", departments);
        model.addAttribute("listOfEmployees", listOfEmployees);
        return "employees/add";
    }

    @PostMapping("/add")
    public String doAdd(@ModelAttribute("employee") @Valid EmployeeDTO employeeDTO, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            List<DepartmentDTO> departments = departmentService.getAll();
            List<EmployeeDTO> listOfEmployees = employeeService.getAll();
            model.addAttribute("departments", departments);
            model.addAttribute("listOfEmployees", listOfEmployees);
            return "employees/add";
        }

        Optional<EmployeeDTO> existingEmployee = employeeService.findByEmail(employeeDTO.getEmail());
        if (existingEmployee.isPresent()) {
            bindingResult.rejectValue("email", "error.employee", "Email đã tồn tại");
            List<DepartmentDTO> departments = departmentService.getAll();
            List<EmployeeDTO> listOfEmployees = employeeService.getAll();
            model.addAttribute("departments", departments);
            model.addAttribute("listOfEmployees", listOfEmployees);
            return "employees/add";
        }
        Optional<EmployeeDTO> existingEmployeeCode = employeeService.findByEmployeeCode(employeeDTO.getEmployeeCode());
        if (existingEmployeeCode.isPresent()) {
            bindingResult.rejectValue("employeeCode", "error.employee", "Code đã tồn tại");
            List<DepartmentDTO> departments = departmentService.getAll();
            List<EmployeeDTO> listOfEmployees = employeeService.getAll();
            model.addAttribute("departments", departments);
            model.addAttribute("listOfEmployees", listOfEmployees);
            return "employees/add";
        }
        employeeService.save(employeeDTO);
        return "redirect:/employees/index";
    }

    @GetMapping("/edit")
    public String showEdit(Model model, Authentication authentication) {
        boolean isAdmin = authorizationService.isAdmin(authentication);
        String loggedInUsername = authentication.getName();
        EmployeeDTO loggedInEmployee = employeeService.findByEmail(loggedInUsername).orElseThrow(() -> new RuntimeException("Employee not found"));
        Optional<EmployeeDTO> employee = employeeService.findOne(loggedInEmployee.getId());
        if (employee.isPresent()) {
            EmployeeDTO employeeDTO = employee.get();
            authorizationService.addUsernameToModel(model, authentication);
            List<DepartmentDTO> departments = departmentService.getAll();
            List<Role> roles = roleRepository.findAll();
            List<EmployeeDTO> listOfEmployees = employeeService.getAll();
            model.addAttribute("roles", roles);
            model.addAttribute("employee", employeeDTO);
            model.addAttribute("departments", departments);
            model.addAttribute("listOfEmployees", listOfEmployees);
            model.addAttribute("isAdmin", isAdmin);
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
            String uploadDir = "D:\\FULLSTACK\\Du an intern\\finalProjectForTrainning\\src\\main\\resources\\static\\image\\";
            String fileName = imageFile.getOriginalFilename();
            File uploadPath = new File(uploadDir);
            File targetFile = new File(uploadPath, fileName);
            imageFile.transferTo(targetFile);
            employeeDTO.setImgUrl(fileName);
        }
        employeeService.updateEmployee(employeeDTO, imageFile);
        return "redirect:/employees/detail";
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

        // Nếu không tìm thấy employee, trả về lỗi 404 Not Found
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
        String htmlContent = templateEngine.process("employees/pdf-export-template", context);

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
            // Xử lý nếu có lỗi khi tạo PDF
            // Ví dụ: throw new RuntimeException("Failed to export profile to PDF");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        employeeService.exportExcel(response);
    }
}
