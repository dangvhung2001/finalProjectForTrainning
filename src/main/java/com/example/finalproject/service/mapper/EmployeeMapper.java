package com.example.finalproject.service.mapper;

import com.example.finalproject.domain.Employee;
import com.example.finalproject.service.dto.EmployeeDTO;

import java.util.Set;

public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    Set<Employee> toEntitySet(Set<EmployeeDTO> dtoList);

    Set<EmployeeDTO> toDtoSet(Set<Employee> entitylist);
}
