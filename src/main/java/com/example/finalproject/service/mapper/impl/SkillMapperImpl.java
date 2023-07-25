package com.example.finalproject.service.mapper.impl;

import com.example.finalproject.domain.Skill;
import com.example.finalproject.service.dto.SkillDTO;
import com.example.finalproject.service.mapper.SkillMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class SkillMapperImpl implements SkillMapper {
    @Override
    public Skill toEntity(SkillDTO dto) {
        if (dto == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setId(dto.getId());
        skill.setName(dto.getName());
        skill.setLevel(dto.getLevel());
        skill.setYearExperience(dto.getYearExperience());
        skill.setMonthExperience(dto.getMonthExperience());
        skill.setDescription(dto.getDescription());
        return skill;
    }

    @Override
    public SkillDTO toDto(Skill entity) {
        if (entity == null) {
            return null;
        }
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(entity.getId());
        skillDTO.setName(entity.getName());
        skillDTO.setLevel(entity.getLevel());
        skillDTO.setYearExperience(entity.getYearExperience());
        skillDTO.setMonthExperience(entity.getMonthExperience());
        skillDTO.setDescription(entity.getDescription());
        return skillDTO;
    }

    @Override
    public List<Skill> toEntity(List<SkillDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        List<Skill> list = new ArrayList<Skill>(dtoList.size());
        for (SkillDTO skillDTO : dtoList) {
            list.add(toEntity(skillDTO));
        }
        return list;
    }

    @Override
    public List<SkillDTO> toDto(List<Skill> entityList) {
        if (entityList == null) {
            return null;
        }

        List<SkillDTO> list = new ArrayList<SkillDTO>(entityList.size());
        for (Skill skill : entityList) {
            list.add(toDto(skill));
        }
        return list;
    }
}
