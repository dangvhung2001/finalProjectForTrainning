package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Department;
import com.example.finalproject.repository.DepartmentRepository;
import com.example.finalproject.service.DepartmentService;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.mapper.impl.DepartmentMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapperImpl departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapperImpl departmentMapper) {
        this.departmentMapper = departmentMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public Page<DepartmentDTO> findAll(String textSearch, Pageable pageable) {
        return departmentRepository.findAllByNameContainingIgnoreCase(textSearch, pageable).map(departmentMapper::toDto);
    }

    @Override
    public Optional<DepartmentDTO> findByName(String name) {
        return departmentRepository.findByNameContainingIgnoreCase(name).map(departmentMapper::toDto);
    }

    @Override
    public Optional<DepartmentDTO> findByDepartmentCode(String departmentCode) {
        return departmentRepository.findByDepartmentCodeContainingIgnoreCase(departmentCode).map(departmentMapper::toDto);
    }

    @Override
    public Optional<DepartmentDTO> findOne(Long id) {
        return departmentRepository.findById(id).map(departmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<DepartmentDTO> getAll() {
        List<Department> departments = departmentRepository.findAll();
        return departmentMapper.toDto(departments);
    }
}
