package com.example.finalproject.service.mapper.impl;

import com.example.finalproject.domain.Department;
import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentMapper implements EntityMapper<DepartmentDTO, Department> {
    @Override
    public Department toEntity(DepartmentDTO dto) {
        if (dto == null) {
            return null;
        }
        Department entity = new Department();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDepartmentCode(dto.getDepartmentCode());
        entity.setDescription(dto.getDescription());
        entity.setIssueDate(dto.getIssueDate());
        if (dto.getParentDepartment() != null) {
            Department parentEntity = new Department();
            parentEntity.setId(dto.getParentDepartment().getId());
            entity.setParent(parentEntity);
        }
        return entity;
    }

    @Override
    public DepartmentDTO toDto(Department entity) {
        if (entity == null) {
            return null;
        }
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDepartmentCode(entity.getDepartmentCode());
        dto.setDescription(entity.getDescription());
        dto.setIssueDate((Date) entity.getIssueDate());
        if (entity.getParent() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(dto.getParentDepartment().getId());
            dto.setParentDepartment(departmentDTO);
        }
        return dto;
    }

    @Override
    public List<Department> toEntity(List<DepartmentDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        List<Department> entityList = new ArrayList<>();
        for (DepartmentDTO dto : dtoList) {
            entityList.add(toEntity(dto));
        }
        return entityList;
    }

    @Override
    public List<DepartmentDTO> toDto(List<Department> entityList) {
        if (entityList == null) {
            return null;
        }
        List<DepartmentDTO> dtoList = new ArrayList<>();
        for (Department entity : entityList) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }


}

