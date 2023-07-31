package com.example.finalproject.service;

import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CertificateService {
    CertificateDTO save(CertificateDTO certificateDTO);

    Page<CertificateDTO> findAll(Pageable pageable);

    Optional<CertificateDTO> findOne(Long id);

    void delete(Long id);

    List<CertificateDTO> getAll();
    List<CertificateDTO> findByEmployeeId(Long employeeId);
}
