package com.example.finalproject.service.impl;

import com.example.finalproject.repository.DepartmentRepository;
import com.example.finalproject.repository.EmployeeRepository;
import com.example.finalproject.repository.ProjectRepository;
import com.example.finalproject.repository.SkillRepository;
import org.springframework.stereotype.Service;

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




    public Map<String, Integer> countEmployeesBySkill() {
        List<Object[]> skillCounts = skillRepository.countEmployeesBySkill();
        Map<String, Integer> result = new HashMap<>();

        for (Object[] obj : skillCounts) {
            String skillName = (String) obj[0];
            Long employeeCount = (Long) obj[1];
            result.put(skillName, employeeCount.intValue());
        }

        return result;
    }
}
