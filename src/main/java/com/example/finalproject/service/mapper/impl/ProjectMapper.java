package com.example.finalproject.service.mapper.impl;

import com.example.finalproject.domain.Employee;
import com.example.finalproject.domain.Experience;
import com.example.finalproject.domain.Project;
import com.example.finalproject.domain.Skill;
import com.example.finalproject.service.dto.ExperienceDTO;
import com.example.finalproject.service.dto.ProjectDTO;
import com.example.finalproject.service.dto.SkillDTO;
import com.example.finalproject.service.mapper.EntityMapper;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectMapper implements EntityMapper<ProjectDTO, Project> {
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
        return entity;
    }

    @Override
    public ProjectDTO toDto(Project entity) {
        if (entity == null) {
            return null;
        }
        ProjectDTO dto = new ProjectDTO();
        dto.setId(dto.getId());
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
