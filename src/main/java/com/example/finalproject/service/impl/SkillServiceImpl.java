package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Skill;
import com.example.finalproject.repository.SkillRepository;
import com.example.finalproject.service.SkillService;
import com.example.finalproject.service.dto.SkillDTO;
import com.example.finalproject.service.mapper.SkillMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper){
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }
    @Override
    public SkillDTO save(SkillDTO skillDTO) {
        Skill skill = skillMapper.toEntity(skillDTO);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    @Override
    public Page<SkillDTO> findAll(Pageable pageable) {
        return skillRepository.findAll(pageable).map(skillMapper::toDto);
    }

    @Override
    public Optional<SkillDTO> findOne(Long id) {
        return skillRepository.findById(id).map(skillMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        skillRepository.deleteById(id);
    }

    @Override
    public List<SkillDTO> getAll() {
        List<Skill> skills = skillRepository.findAll();
        return skillMapper.toDto(skills);
    }
}
