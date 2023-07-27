package com.example.finalproject.service;


import com.example.finalproject.service.dto.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentDTO save(DepartmentDTO departmentDTO);

    Page<DepartmentDTO> findAll(String textSearch,Pageable pageable);

    Optional<DepartmentDTO> findOne(Long id);

    void delete(Long id);

    List<DepartmentDTO> getAll();



}
