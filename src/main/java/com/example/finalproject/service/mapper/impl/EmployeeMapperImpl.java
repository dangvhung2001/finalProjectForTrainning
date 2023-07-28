package com.example.finalproject.service.mapper.impl;

import com.example.finalproject.domain.Department;
import com.example.finalproject.domain.Employee;
import com.example.finalproject.domain.Role;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.mapper.EmployeeMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public Employee toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setImgUrl(dto.getImgUrl());
        employee.setFirstname(dto.getFirstname());
        employee.setLastname(dto.getLastname());
        employee.setPassword(dto.getPassword());
        employee.setCreatedAt(dto.getCreatedAt());
        employee.setSex( dto.getSex());
        employee.setMaritalStatus(dto.getMaritalStatus());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setCitizenCode(dto.getCitizenCode());
        employee.setIssueDate(dto.getIssueDate());
        employee.setPlaceOfIssue(dto.getPlaceOfIssue());
        employee.setEmployeeCode(dto.getEmployeeCode());
        employee.setEmail(dto.getEmail());
        employee.setAddress(dto.getAddress());
        employee.setPhone(dto.getPhone());
        employee.setStartDate(dto.getStartDate());
        employee.setSalaryCoefficient(dto.getSalaryCoefficient());
        employee.setSalary(dto.getSalary());
        employee.setPosition(dto.getPosition());
        employee.setDepartmentId(dto.getDepartmentId());
        employee.setEducationLevel(dto.getEducationLevel());
        return employee;
    }

    @Override
    public EmployeeDTO toDto(Employee entity) {
        if (entity == null) {
            return null;
        }
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(entity.getId());
        employeeDTO.setImgUrl(entity.getImgUrl());
        employeeDTO.setFirstname(entity.getFirstname());
        employeeDTO.setLastname(entity.getLastname());
        employeeDTO.setPassword(entity.getPassword());
        employeeDTO.setCreatedAt(entity.getCreatedAt());
        employeeDTO.setSex(entity.getSex());
        employeeDTO.setMaritalStatus(entity.getMaritalStatus());
        employeeDTO.setDateOfBirth(entity.getDateOfBirth());
        employeeDTO.setCitizenCode(entity.getCitizenCode());
        employeeDTO.setIssueDate(entity.getIssueDate());
        employeeDTO.setPlaceOfIssue(entity.getPlaceOfIssue());
        employeeDTO.setEmployeeCode(entity.getEmployeeCode());
        employeeDTO.setEmail(entity.getEmail());
        employeeDTO.setAddress(entity.getAddress());
        employeeDTO.setPhone(entity.getPhone());
        employeeDTO.setStartDate(entity.getStartDate());
        employeeDTO.setSalaryCoefficient(entity.getSalaryCoefficient());
        employeeDTO.setSalary(entity.getSalary());
        employeeDTO.setPosition(entity.getPosition());
        employeeDTO.setEducationLevel(entity.getEducationLevel());
        employeeDTO.setDepartmentId(entity.getDepartmentId());
        Department department = entity.getDepartment();
        if (department != null) {
            employeeDTO.setDepartmentName(department.getName());
        }

        Set<Role> roles = entity.getRoles();
        if (roles != null) {
            employeeDTO.setRoles(roles.stream().map(Role::getRoleName).collect(Collectors.toSet()));
        }
        return employeeDTO;
    }

    @Override
    public List<Employee> toEntity(List<EmployeeDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        List<Employee> list = new ArrayList<Employee>(dtoList.size());
        for (EmployeeDTO employeeDTO : dtoList) {
            list.add(toEntity(employeeDTO));
        }
        return list;
    }

    @Override
    public List<EmployeeDTO> toDto(List<Employee> entityList) {
        if (entityList == null) {
            return null;
        }
        List<EmployeeDTO> list = new ArrayList<EmployeeDTO>(entityList.size());
        for (Employee employee : entityList) {
            list.add(toDto(employee));
        }
        return list;
    }
}
