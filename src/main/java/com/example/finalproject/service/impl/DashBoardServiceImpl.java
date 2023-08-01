package com.example.finalproject.service.impl;

import com.example.finalproject.repository.DepartmentRepository;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.ProjectRepository;
import com.example.finalproject.repository.SkillRepository;
import com.example.finalproject.service.dto.SkillDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DashBoardServiceImpl {
    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final ProjectRepository projectRepository;

    private final SkillRepository skillRepository;

    public DashBoardServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ProjectRepository projectRepository,SkillRepository skillRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    public long getTotalEmployees() {
        return employeeRepository.count();
    }

    public long getTotalDepartment() {
        return departmentRepository.count();
    }

    public long getTotalProject() {
        return projectRepository.count();
    }

    public List<SkillDTO> getLanguageUsed() {
        List<Object[]> languageStatistics = skillRepository.getLanguageUsageStatistics();
        List<SkillDTO> languageDTOList = new ArrayList<>();

        for (Object[] result : languageStatistics) {
            String languageName = (String) result[0];
            Long languageUsageCount = (Long) result[1];
            SkillDTO languageDTO = new SkillDTO(languageName, languageUsageCount.intValue());
            languageDTOList.add(languageDTO);
        }

        return languageDTOList;
    }
}
