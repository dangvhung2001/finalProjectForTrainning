package com.example.finalproject.service;


import com.example.finalproject.domain.Employee;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentDTO save(DepartmentDTO departmentDTO);

    Page<DepartmentDTO> findAll(String textSearch, Pageable pageable);

    Optional<DepartmentDTO> findByName(String name);

    Optional<DepartmentDTO> findByDepartmentCode(String departmentCode);

    Optional<DepartmentDTO> findOne(Long id);

    void delete(Long id);

    List<DepartmentDTO> getAll();

    Long countByDepartmentId(Long departmentId);

    List<EmployeeDTO> findByDepartmentId(Long departmentId);
}
