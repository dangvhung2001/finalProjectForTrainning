package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Experience;
import com.example.finalproject.domain.Project;
import com.example.finalproject.repository.ExperienceRepository;
import com.example.finalproject.repository.ProjectRepository;
import com.example.finalproject.service.ProjectService;
import com.example.finalproject.service.dto.ProjectDTO;
import com.example.finalproject.service.mapper.impl.ExperienceMapper;
import com.example.finalproject.service.mapper.impl.ProjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper){
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }
    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(projectMapper::toDto);
    }

    @Override
    public Optional<ProjectDTO> findOne(Long id) {
        return projectRepository.findById(id).map(projectMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectDTO> getAll() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toDto(projects);
    }
}
