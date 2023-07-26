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

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               RoleRepository roleRepository,
                               EmployeeMapper employeeMapper,
                               PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        if (employeeDTO.getRoles() != null) {
            Set<Role> roles = employeeDTO
                    .getRoles()
                    .stream()
                    .map(roleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            employee.setRoles(roles);
        }
        employee = employeeRepository.save(employee);
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
//        return employeeRepository.findOneByEmailIgnoreCase(email).map(employeeMapper::toDto);
        return null;
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
