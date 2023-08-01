package com.example.finalproject.service;

import com.example.finalproject.service.dto.ExperienceDTO;
import com.example.finalproject.service.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    ProjectDTO save(ProjectDTO projectDTO);

    Page<ProjectDTO> findAll(Pageable pageable);

    Optional<ProjectDTO> findOne(Long id);

    void delete(Long id);

    List<ProjectDTO> getAll();

    int getTotalPmCount();
}
