package com.example.finalproject.controller;

import com.example.finalproject.domain.Role;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.service.DepartmentService;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.impl.MailSenderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final RoleRepository roleRepository;
    private final MailSenderService mailService;

    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService,
                              RoleRepository roleRepository,
<<<<<<< HEAD:src/main/java/com/example/finalproject/Controller/EmployeeController.java
                              EmployeeRepository employeeRepository,
                              MailSenderService mailService) {
=======
                              EmployeeRepository employeeRepository) {
>>>>>>> 65f3340 (fix:fix bug addEmployee , feat: Login,changePassword Layout):src/main/java/com/example/finalproject/controller/EmployeeController.java
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
<<<<<<< HEAD:src/main/java/com/example/finalproject/Controller/EmployeeController.java
        this.mailService = mailService;
=======
>>>>>>> 65f3340 (fix:fix bug addEmployee , feat: Login,changePassword Layout):src/main/java/com/example/finalproject/controller/EmployeeController.java
    }

    @GetMapping("homePage")
    public String homePage() {
        return "dashboard/index";
    }

    @GetMapping("/index")
<<<<<<< HEAD:src/main/java/com/example/finalproject/Controller/EmployeeController.java
    public String index(@RequestParam(required = false, defaultValue = "") String textSearch, Pageable pageable, Model model, Authentication authentication) {
        String username = authentication.getName();
        Page<EmployeeDTO> listOfEmployees = employeeService.findAll(textSearch, pageable);
=======
    public String index(@RequestParam(required = false,defaultValue = "") String textSearch, Pageable pageable, Model model, Authentication authentication) {
        String username = authentication.getName();
        Page<EmployeeDTO> listOfEmployees = employeeService.findAll(textSearch,pageable);
>>>>>>> 65f3340 (fix:fix bug addEmployee , feat: Login,changePassword Layout):src/main/java/com/example/finalproject/controller/EmployeeController.java
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

    @GetMapping("/add")
    public String showAdd(Model model) {
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
    public String doAdd(@ModelAttribute("employee") @Valid EmployeeDTO employeeDTO, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "employees/add";
        }
        Optional<EmployeeDTO> existingEmployee = employeeService.findByEmail(employeeDTO.getEmail());
        if (existingEmployee.isPresent()) {
            bindingResult.rejectValue("email", "error.employee", "Email đã tồn tại");
            return "employees/add";
        }
        Optional<EmployeeDTO> existingEmployeeCode = employeeService.findByEmployeeCode(employeeDTO.getEmployeeCode());
        if (existingEmployeeCode.isPresent()) {
            bindingResult.rejectValue("employeeCode", "error.employee", "Code đã tồn tại");
            return "employees/add";
<<<<<<< HEAD:src/main/java/com/example/finalproject/Controller/EmployeeController.java
=======
        }
        Optional<EmployeeDTO> existingEmployeePhone = employeeService.findByPhone(employeeDTO.getPhone());
        if (existingEmployeePhone.isPresent()) {
            bindingResult.rejectValue("phone", "error.employee", "Phone đã tồn tại");
            return "employees/add";
>>>>>>> 65f3340 (fix:fix bug addEmployee , feat: Login,changePassword Layout):src/main/java/com/example/finalproject/controller/EmployeeController.java
        }
        employeeService.save(employeeDTO);
        return "redirect:/employees/index";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model
    ) {
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
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "employees/edit";
        }
        employeeDTO.setId(id);
        employeeService.save(employeeDTO);
        return "redirect:/employees/index";
    }

    @GetMapping("/delete/{id}")
    public String doDelete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employees/index";
    }
}
