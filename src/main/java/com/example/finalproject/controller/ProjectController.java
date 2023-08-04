package com.example.finalproject.controller;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.service.DepartmentService;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.dto.ProjectDTO;
import com.example.finalproject.service.impl.ProjectServiceImpl;
import com.example.finalproject.service.mapper.impl.ProjectMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private final ProjectServiceImpl projectServiceImpl;

    private final RoleRepository roleRepository;

    private final DepartmentService departmentService;

    private final EmployeeService employeeService;

    private final ProjectMapperImpl projectMapper;

    public ProjectController(ProjectServiceImpl projectServiceImpl, ProjectMapperImpl projectMapper,EmployeeService employeeService,RoleRepository roleRepository,DepartmentService departmentService){
        this.projectMapper = projectMapper;
        this.projectServiceImpl = projectServiceImpl;
        this.employeeService = employeeService;
        this.roleRepository = roleRepository;
        this.departmentService = departmentService;
    }

    List<EmployeeDTO> selectedEmployee = new ArrayList<>();

    @GetMapping("/detail")
    public String showDetail(Model model, @RequestParam(required = false) String textSearch, Pageable pageable) {
        Page<ProjectDTO> projectDTOS = projectServiceImpl.findAll(pageable);
        model.addAttribute("projects",projectDTOS);
        return "project/index";
    }

    @GetMapping("/detail/{id}")
    public String detailProject(@PathVariable Long id, Model model,Pageable pageable) {
        Optional<ProjectDTO> projects = projectServiceImpl.findOne(id);
        if (projects.isPresent()) {
            ProjectDTO projectDTO = projects.get();
            model.addAttribute("projects", projectDTO);
            return "project/detail";
        } else {
            return "redirect:/project/detail";
        }
    }

    @GetMapping("/create")
    public String showAdd(Model model, Pageable pageable, HttpSession session) {
        List<EmployeeDTO> listOfEmployees = employeeService.getAll();
        model.addAttribute("listOfEmploy", listOfEmployees);
        model.addAttribute("project", new ProjectDTO());

        // Kiểm tra nếu có danh sách nhân viên đã chọn trong session, thì gán nó vào model
        List<EmployeeDTO> selectedEmployees = (List<EmployeeDTO>) session.getAttribute("selectedEmployees");
        if (selectedEmployees != null && !selectedEmployees.isEmpty()) {
            model.addAttribute("selectedEmployees", selectedEmployees);
        }

        return "project/create";
    }

    @PostMapping("/add")
    public ModelAndView doAdd(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("project/create");
            return modelAndView;
        }
        List<EmployeeDTO> selectedEmployees = (List<EmployeeDTO>) session.getAttribute("selectedEmployees");
        if (selectedEmployees != null) {
            Set<EmployeeDTO> employeeSet = new HashSet<>(selectedEmployees);
            projectDTO.setEmployees(employeeSet);
            projectServiceImpl.save(projectDTO);
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/project/detail");
        modelAndView.addObject("projectDTO", projectDTO);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        projectServiceImpl.delete(id);
        return "redirect:/project/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable) {
        Optional<ProjectDTO> projects = projectServiceImpl.findOne(id);
        if (projects.isPresent()) {
            ProjectDTO projectDTO = projects.get();
            model.addAttribute("projects", projectDTO);
            return "project/edit";
        } else {
            return "redirect:/project/detail";
        }
    }


    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Long id, @ModelAttribute("projects") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "project/edit";
        }
        projectDTO.setId(id);
        projectServiceImpl.save(projectDTO);
        return "redirect:/project/detail";
    }

    @GetMapping("/show/employee")
    public String index(@RequestParam(required = false, defaultValue = "") String textSearch, Pageable pageable, Model model) {
        Page<EmployeeDTO> listOfEmployees = employeeService.findAll(textSearch, pageable);
        model.addAttribute("listOfEmployees", listOfEmployees);
        return "project/create-employee";
    }



    @PostMapping("/add/employee")
    public ModelAndView doAdd(@RequestParam(value = "selectedEmployeeCodes", required = false) List<String> selectedEmployees,
                              HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
        if (selectedEmployees == null || selectedEmployees.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("project/create-employee");
            return modelAndView;
        }

        List<EmployeeDTO> selectedEmployee = new ArrayList<>();
        for (String employeeCode : selectedEmployees) {
            Long employeeId = Long.parseLong(employeeCode);
            Optional<EmployeeDTO> employee = employeeService.findOne(employeeId);
            if (employee.isPresent()) {
                selectedEmployee.add(employee.get());
            }
        }

        // Lưu danh sách nhân viên đã chọn vào session
        session.setAttribute("selectedEmployees", selectedEmployee);

        ModelAndView modelAndView = new ModelAndView("redirect:/project/create");
        return modelAndView;
    }


    @GetMapping("/employee/delete/{id}")
    public String doDelete(@PathVariable Long id, HttpSession session, Model model) {
        List<EmployeeDTO> selectedEmployees = (List<EmployeeDTO>) session.getAttribute("selectedEmployees");
        if (selectedEmployees != null) {
            // Xóa employee khỏi danh sách selectedEmployee dựa vào id
            selectedEmployees.removeIf(employee -> employee.getId().equals(id));
            // Lưu danh sách selectedEmployees mới vào session sau khi xóa
            session.setAttribute("selectedEmployees", selectedEmployees);
        }

        // Cập nhật lại danh sách selectedEmployees trong model để hiển thị trong form create
        model.addAttribute("selectedEmployees", selectedEmployees);

        return "redirect:/project/create"; // Chuyển hướng về trang create để hiển thị danh sách nhân viên đã chọn
    }
}

