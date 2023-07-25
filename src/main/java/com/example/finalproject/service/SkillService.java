package com.example.finalproject.service;

import com.example.finalproject.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SkillService {
    SkillDTO save(SkillDTO skillDTO);

    Page<SkillDTO> findAll(Pageable pageable);

    Optional<SkillDTO> findOne(Long id);

    void delete(Long id);
    List<SkillDTO> getAll();
}