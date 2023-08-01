package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Certificate;
import com.example.finalproject.domain.Employee;
import com.example.finalproject.repository.CertificateRepository;
import com.example.finalproject.service.CertificateService;
import com.example.finalproject.service.EmployeeService;
import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.dto.EmployeeDTO;
import com.example.finalproject.service.mapper.CertificateMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final EmployeeService employeeService;

    public CertificateServiceImpl(CertificateRepository certificateRepository, CertificateMapper certificateMapper, EmployeeService employeeService) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.employeeService = employeeService;
    }


    @Override
    public CertificateDTO save(CertificateDTO certificateDTO) {
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    @Override
    public Page<CertificateDTO> findAll(Pageable pageable) {
        return certificateRepository.findAll(pageable).map(certificateMapper::toDto);
    }

    @Override
    public Optional<CertificateDTO> findOne(Long id) {
        return certificateRepository.findById(id).map(certificateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        certificateRepository.deleteById(id);
    }

    @Override
    public List<CertificateDTO> getAll() {
        List<Certificate> certificates = certificateRepository.findAll();
        return certificateMapper.toDto(certificates);
    }

    @Override
    public List<CertificateDTO> findByEmployeeId(Long employeeId) {
        List<Certificate> certificates = certificateRepository.findByEmployeeId(employeeId);
        return certificateMapper.toDto(certificates);
    }
}
