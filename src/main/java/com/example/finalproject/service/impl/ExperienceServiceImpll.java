package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Experience;
import com.example.finalproject.repository.ExperienceRepository;
import com.example.finalproject.service.ExperienceService;
import com.example.finalproject.service.dto.ExperienceDTO;
import com.example.finalproject.service.mapper.impl.ExperienceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ExperienceServiceImpll implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    public ExperienceServiceImpll(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper){
        this.experienceMapper = experienceMapper;
        this.experienceRepository = experienceRepository;
    }
    @Override
    public ExperienceDTO save(ExperienceDTO experienceDTO) {
        Experience experience = experienceMapper.toEntity(experienceDTO);
        experience = experienceRepository.save(experience);
        return experienceMapper.toDto(experience);
    }

    @Override
    public Page<ExperienceDTO> findAll(Pageable pageable) {
        return experienceRepository.findAll(pageable).map(experienceMapper::toDto);
    }

    @Override
    public Optional<ExperienceDTO> findOne(Long id) {
        return experienceRepository.findById(id).map(experienceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        experienceRepository.deleteById(id);
    }

    @Override
    public List<ExperienceDTO> getAll() {
        List<Experience> experiences = experienceRepository.findAll();
        return experienceMapper.toDto(experiences);
    }
}
