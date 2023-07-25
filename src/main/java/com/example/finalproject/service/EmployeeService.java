package com.example.finalproject.service;

import com.example.finalproject.domain.Employee;
import com.example.finalproject.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO save(EmployeeDTO notifyDTO);

    Page<EmployeeDTO> findAll(String textSearch, Pageable pageable);

    Optional<EmployeeDTO> findOne(Long id);

    void delete(Long id);

    Page<EmployeeDTO> findAllEmployee(Pageable pageable);
    Optional<EmployeeDTO> findByEmail(String email);

    void saveEmployee(EmployeeDTO employeeDTO);
    Employee findUserByEmail(String email);
    List<EmployeeDTO> getAll();
}
