package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Employee;
import com.example.finalproject.domain.Role;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.mapper.EmployeeMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeMapper employeeMapper;
    private PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               RoleRepository roleRepository,
                               EmployeeMapper employeeMapper,
                               PasswordEncoder passwordEncoder,
                               MailSenderService mailSenderService) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setManager(employeeRepository.findById(employeeDTO.getDepartmentId()).get());
        String to = employee.getEmail();
        String subject = "Chào mừng bạn đến với công ty chúng tôi";
        String body = "Xin chào " + employee.getFirstname() + " " + employee.getLastname() + ",\n\n" +
                "Chúng tôi rất vui mừng thông báo rằng bạn đã trở thành thành viên mới của công ty chúng tôi.\n" +
                "Cảm ơn bạn đã tham gia và chúc bạn có những trải nghiệm tuyệt vời tại công ty.\n\n" +
                "Trân trọng,\n" +
                "Mật khẩu của bạn là " + employee.getPassword() + "\n\n" +
                "Lưu ý: Đây là một email tự động, vui lòng không trả lời. Hãy thay đổi mật khẩu của bạn ngay sau khi nhận được email này để bảo mật thông tin cá nhân.\\n\"";
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        if (employeeDTO.getRoles() != null) {
            Set<Role> roles = employeeDTO
                    .getRoles()
                    .stream()
                    .map(roleRepository::findByRoleName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            employee.setRoles(roles);
        }
        employee = employeeRepository.save(employee);
        mailSenderService.sendNewMail(to, subject, body);
        return employeeMapper.toDto(employee);
    }

    @Override
    public Page<EmployeeDTO> findAll(String textSearch, Pageable pageable) {
        return employeeRepository.findAllByLastnameOrEmailContainingIgnoreCase(textSearch, textSearch, pageable).map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDTO> findOne(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<EmployeeDTO> findAllEmployee(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDTO> findByEmail(String email) {
        return employeeRepository.findByEmailIgnoreCase(email).map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDTO> findByEmployeeCode(String employeeCode) {
        return employeeRepository.findByEmployeeCodeContainingIgnoreCase(employeeCode).map(employeeMapper::toDto);
    }


    @Override
    public void saveEmployee(EmployeeDTO employeeDTO) {

    }

    @Override
    public Employee findUserByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    @Override
    public List<EmployeeDTO> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toDto(employees);
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO, MultipartFile imageFile) {
        Optional<Employee> oldEmployee = employeeRepository.findById(employeeDTO.getId());
        if (oldEmployee.isPresent()) {
            Employee existingEmployee = oldEmployee.get();
            if (employeeDTO.getPassword() == null || employeeDTO.getPassword().isEmpty()) {
                employeeDTO.setPassword(existingEmployee.getPassword());
//                employeeDTO.setSalary(existingEmployee.getSalary());
//                employeeDTO.setSalaryCoefficient(existingEmployee.getSalaryCoefficient());
//                employeeDTO.setDepartmentId(existingEmployee.getDepartmentId());
//                employeeDTO.setEmployeeCode(existingEmployee.getEmployeeCode());
//                employeeDTO.setStartDate(existingEmployee.getStartDate());
            }
            existingEmployee.setFirstname(employeeDTO.getFirstname());
            existingEmployee.setLastname(employeeDTO.getLastname());
            existingEmployee.setDateOfBirth(employeeDTO.getDateOfBirth());
            existingEmployee.setEducationLevel(employeeDTO.getEducationLevel());
            existingEmployee.setPosition(employeeDTO.getPosition());
            existingEmployee.setPhone(employeeDTO.getPhone());
            existingEmployee.setAddress(employeeDTO.getAddress());
            existingEmployee.setEmail(employeeDTO.getEmail());
            existingEmployee.setIssueDate(employeeDTO.getIssueDate());
            existingEmployee.setCitizenCode(employeeDTO.getCitizenCode());
            existingEmployee.setSex(employeeDTO.getSex());
            existingEmployee.setPlaceOfIssue(employeeDTO.getPlaceOfIssue());
            existingEmployee.setImgUrl(employeeDTO.getImgUrl());
            existingEmployee.setDepartmentId(employeeDTO.getDepartmentId());

            employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }
    @Override
    public void exportExcel(HttpServletResponse response) throws IOException {
        // Đối tượng Workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách nhân viên");

        // Dữ liệu từ server
        List<Employee> listOfEmployees = employeeRepository.findAll();

        String[] columns = {"ID nhân viên", "Họ và tên", "Địa chỉ", "Ngày sinh", "Giới tính", "SĐT", "Chức vụ", "Email", "Hệ số lương", "Lương cơ bản", "Ảnh"};

        // Tạo header row
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < columns.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(columns[col]);
        }

        // Tạo dòng dữ liệu cho từng nhân viên
        for (int rowIdx = 0; rowIdx < listOfEmployees.size(); rowIdx++) {
            Row row = sheet.createRow(rowIdx + 1);
            Employee employee = listOfEmployees.get(rowIdx);

            row.createCell(0).setCellValue(employee.getEmployeeCode());
            row.createCell(1).setCellValue(employee.getFirstname() + " " + employee.getLastname());
            row.createCell(2).setCellValue(employee.getAddress());
            row.createCell(3).setCellValue(employee.getDateOfBirth().toString());
            row.createCell(4).setCellValue(employee.getSex() == 0 ? "Nam" : "Nữ");
            row.createCell(5).setCellValue(employee.getPhone());
            row.createCell(6).setCellValue(employee.getPosition());
            row.createCell(7).setCellValue(employee.getEmail());
            row.createCell(8).setCellValue(employee.getSalaryCoefficient());
            row.createCell(9).setCellValue(employee.getSalary());
            row.createCell(9).setCellValue(employee.getImgUrl());
            employeeMapper.toDto(employee);
        }

        // Cài đặt header và response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");

        // Ghi dữ liệu vào response
        workbook.write(response.getOutputStream());
        workbook.close();
        employeeMapper.toDto(listOfEmployees);
    }
}
