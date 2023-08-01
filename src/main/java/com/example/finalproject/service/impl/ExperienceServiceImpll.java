package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Experience;
import com.example.finalproject.repository.ExperienceRepository;
import com.example.finalproject.service.ExperienceService;
import com.example.finalproject.service.dto.ExperienceDTO;
import com.example.finalproject.service.mapper.impl.ExperienceMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpll implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapperImpl experienceMapperImpl;

    public ExperienceServiceImpll(ExperienceRepository experienceRepository, ExperienceMapperImpl experienceMapperImpl) {
        this.experienceMapperImpl = experienceMapperImpl;
        this.experienceRepository = experienceRepository;
    }

    @Override
    public ExperienceDTO save(ExperienceDTO experienceDTO) {
        Experience experience = experienceMapperImpl.toEntity(experienceDTO);
        experience = experienceRepository.save(experience);
        return experienceMapperImpl.toDto(experience);
    }

    @Override
    public Page<ExperienceDTO> findAll(Pageable pageable) {
        return experienceRepository.findAll(pageable).map(experienceMapperImpl::toDto);
    }

    @Override
    public Optional<ExperienceDTO> findOne(Long id) {
        return experienceRepository.findById(id).map(experienceMapperImpl::toDto);
    }

    @Override
    public void delete(Long id) {
        experienceRepository.deleteById(id);
    }

    @Override
    public List<ExperienceDTO> getAll() {
        List<Experience> experiences = experienceRepository.findAll();
        return experienceMapperImpl.toDto(experiences);
    }

    @Override
    public Optional<ExperienceDTO> findByName(String name) {
        return experienceRepository.findByNameExperienceContainingIgnoreCase(name).map(experienceMapperImpl::toDto);
    }
}
