package com.example.finalproject.service.mapper.impl;

import com.example.finalproject.domain.*;
import com.example.finalproject.service.dto.ProjectDTO;
import com.example.finalproject.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class ProjectMapperImpl implements EntityMapper<ProjectDTO, Project> {

    private final EmployeeMapperImpl employeeMapper;

    public ProjectMapperImpl(EmployeeMapperImpl employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Project toEntity(ProjectDTO dto) {
        if (dto == null) {
            return null;
        }
        Project entity = new Project();
        entity.setId(dto.getId());
        entity.setNameProject(dto.getNameProject());
        entity.setLink(dto.getLink());
        entity.setLanguage(dto.getLanguage());
        entity.setWorkplace(dto.getWorkplace());
        entity.setProjectCost(dto.getProjectCost());
        entity.setProjectResources(dto.getProjectResources());
        entity.setOs(dto.getOs());
        entity.setFramework(dto.getFramework());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setDescription(dto.getDescription());
        entity.setPmId(dto.getPmId());
        entity.setEmployees(employeeMapper.toEntitySet(dto.getEmployees()));
        return entity;
    }

    @Override
    public ProjectDTO toDto(Project entity) {
        if (entity == null) {
            return null;
        }
        ProjectDTO dto = new ProjectDTO();
        dto.setId(entity.getId());
        dto.setNameProject(entity.getNameProject());
        dto.setLink(entity.getLink());
        dto.setLanguage(entity.getLanguage());
        dto.setWorkplace(entity.getWorkplace());
        dto.setProjectCost(entity.getProjectCost());
        dto.setProjectResources(entity.getProjectResources());
        dto.setOs(entity.getOs());
        dto.setFramework(entity.getFramework());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDescription(entity.getDescription());
        dto.setPmId(entity.getPmId());
        dto.setEmployees(employeeMapper.toDtoSet(entity.getEmployees()));

        Employee pm = entity.getPm();
        if (pm != null) {
            dto.setNamePm(pm.getFirstname());
        } else {
            dto.setNamePm("Chưa có nhân viên quản lý");
        }
        return dto;
    }

    @Override
    public List<Project> toEntity(List<ProjectDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        List<Project> list = new ArrayList<Project>(dtoList.size());
        for (ProjectDTO projectDTO : dtoList) {
            list.add(toEntity(projectDTO));
        }
        return list;
    }

    @Override
    public List<ProjectDTO> toDto(List<Project> entityList) {
        if (entityList == null) {
            return null;
        }

        List<ProjectDTO> list = new ArrayList<ProjectDTO>(entityList.size());
        for (Project project : entityList) {
            list.add(toDto(project));
        }
        return list;
    }
}
