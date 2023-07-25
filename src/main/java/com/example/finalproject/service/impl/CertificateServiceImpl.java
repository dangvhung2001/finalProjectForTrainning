package com.example.finalproject.service.impl;

import com.example.finalproject.domain.Certificate;
import com.example.finalproject.repository.CertificateRepository;
import com.example.finalproject.service.CertificateService;
import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.mapper.CertificateMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    public CertificateServiceImpl(CertificateRepository certificateRepository, CertificateMapper certificateMapper){
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
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
}
