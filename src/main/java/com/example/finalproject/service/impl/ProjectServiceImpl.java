package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Project;
import com.example.finalproject.repository.ProjectRepository;
import com.example.finalproject.service.ProjectService;
import com.example.finalproject.service.dto.ProjectDTO;
import com.example.finalproject.service.mapper.impl.ProjectMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapperImpl projectMapperImpl;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapperImpl projectMapperImpl) {
        this.projectRepository = projectRepository;
        this.projectMapperImpl = projectMapperImpl;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        Project project = projectMapperImpl.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapperImpl.toDto(project);
    }

    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectMapperImpl::toDto);
    }

    @Override
    public Optional<ProjectDTO> findOne(Long id) {
        return projectRepository.findById(id).map(projectMapperImpl::toDto);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectDTO> getAll() {
        List<Project> projects = projectRepository.findAll();
        return projectMapperImpl.toDto(projects);
    }

    @Override
    public int getTotalPmCount() {
        List<Project> projects = projectRepository.findAll();
        int totalPmCount = 0;

        for (Project project : projects) {
            if (project.getPm() != null) {
                totalPmCount++;
            }
        }

        return totalPmCount;
    }

}
