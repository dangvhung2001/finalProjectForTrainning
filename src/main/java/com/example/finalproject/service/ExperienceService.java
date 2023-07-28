package com.example.finalproject.service;

import com.example.finalproject.service.dto.DepartmentDTO;
import com.example.finalproject.service.dto.ExperienceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceDTO save(ExperienceDTO experienceDTO);

    Page<ExperienceDTO> findAll(Pageable pageable);

    Optional<ExperienceDTO> findOne(Long id);

    void delete(Long id);

    List<ExperienceDTO> getAll();
}
