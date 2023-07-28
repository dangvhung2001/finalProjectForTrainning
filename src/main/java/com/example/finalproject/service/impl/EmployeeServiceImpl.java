package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Employee;
import com.example.finalproject.domain.Role;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.RoleRepository;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.mapper.EmployeeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        String to = employee.getEmail();
        String subject = "Chào mừng bạn đến với công ty chúng tôi";
        String body = "Xin chào " + employee.getFirstname() + " " + employee.getLastname() + ",\n\n" +
                "Chúng tôi rất vui mừng thông báo rằng bạn đã trở thành thành viên mới của công ty chúng tôi.\n" +
                "Cảm ơn bạn đã tham gia và chúc bạn có những trải nghiệm tuyệt vời tại công ty.\n\n" +
                "Trân trọng,\n" +
                "Mật khẩu của bạn là " + employee.getPassword() + "\n" +
                "Ban quản trị";

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
}
