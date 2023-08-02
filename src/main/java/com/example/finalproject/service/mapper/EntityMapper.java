package com.example.finalproject.service.mapper;

import com.example.finalproject.domain.Employee;
import com.example.finalproject.service.dto.EmployeeDTO;

import java.util.List;
import java.util.Set;


public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);



    }
