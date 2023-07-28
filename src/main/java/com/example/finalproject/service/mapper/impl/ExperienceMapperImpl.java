package com.example.finalproject.service.mapper.impl;

import com.example.finalproject.domain.Experience;
import com.example.finalproject.service.dto.ExperienceDTO;
import com.example.finalproject.service.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ExperienceMapperImpl implements EntityMapper<ExperienceDTO, Experience> {
    @Override
    public Experience toEntity(ExperienceDTO dto) {
        if (dto == null) {
            return null;
        }
        Experience entity = new Experience();
        entity.setId(dto.getId());
        entity.setNameExperience(dto.getName_experience());
        entity.setTimeStart(dto.getTimeStart());
        entity.setTimeEnd(dto.getTimeEnd());
        entity.setLanguage(dto.getLanguage());
        entity.setLink(dto.getLink());
        entity.setWorkplace(dto.getWorkplace());
        entity.setPosition(dto.getPosition());
        entity.setTeamSize(dto.getTeamSize());
        entity.setDescription(dto.getDescription());
        entity.setOs(dto.getOs());
        entity.setFramework(dto.getFramework());
        return entity;
    }

    @Override
    public ExperienceDTO toDto(Experience entity) {
        if (entity == null) {
            return null;
        }
        ExperienceDTO dto = new ExperienceDTO();
        dto.setId(entity.getId());
        dto.setName_experience(entity.getNameExperience());
        dto.setTimeStart(entity.getTimeStart());
        dto.setTimeEnd(entity.getTimeEnd());
        dto.setLanguage(entity.getLanguage());
        dto.setLink(entity.getLink());
        dto.setWorkplace(entity.getWorkplace());
        dto.setPosition(entity.getPosition());
        dto.setTeamSize(entity.getTeamSize());
        dto.setDescription(entity.getDescription());
        dto.setOs(entity.getOs());
        dto.setFramework(entity.getFramework());
        return dto;
    }

    @Override
    public List<Experience> toEntity(List<ExperienceDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        List<Experience> entityList = new ArrayList<>();
        for (ExperienceDTO dto : dtoList) {
            entityList.add(toEntity(dto));
        }
        return entityList;
    }

    @Override
    public List<ExperienceDTO> toDto(List<Experience> entityList) {
        if (entityList == null) {
            return null;
        }
        List<ExperienceDTO> dtoList = new ArrayList<>();
        for (Experience entity : entityList) {
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }


}
