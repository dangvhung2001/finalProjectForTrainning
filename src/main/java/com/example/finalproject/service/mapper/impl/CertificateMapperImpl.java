package com.example.finalproject.service.mapper.impl;

import com.example.finalproject.domain.Certificate;
import com.example.finalproject.domain.Employee;
import com.example.finalproject.service.dto.CertificateDTO;
import com.example.finalproject.service.mapper.CertificateMapper;
import com.example.finalproject.service.mapper.EmployeeMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CertificateMapperImpl implements CertificateMapper {

    private final EmployeeMapper employeeMapper;
    public CertificateMapperImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }
    @Override
    public Certificate toEntity(CertificateDTO dto) {
        if (dto == null) {
            return null;
        }
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setNameCertificate(dto.getNameCertificate());
        certificate.setIssueDate(dto.getIssueDate());
        certificate.setExpirationDate(dto.getExpirationDate());
        certificate.setDescription(dto.getDescription());
        certificate.setCertificationOfficer(dto.getCertificationOfficer());
        certificate.setEmployee(employeeMapper.toEntity(dto.getEmployee()));
        return certificate;
    }

    @Override
    public CertificateDTO toDto(Certificate entity) {
        if (entity == null) {
            return null;
        }
        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setId(entity.getId());
        certificateDTO.setNameCertificate(entity.getNameCertificate());
        certificateDTO.setIssueDate(entity.getIssueDate());
        certificateDTO.setExpirationDate(entity.getExpirationDate());
        certificateDTO.setDescription(entity.getDescription());
        certificateDTO.setCertificationOfficer(entity.getCertificationOfficer());
        certificateDTO.setEmployee(employeeMapper.toDto(entity.getEmployee()));
        return certificateDTO;
    }

    @Override
    public List<Certificate> toEntity(List<CertificateDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Certificate> list = new ArrayList<Certificate>(dtoList.size());
        for (CertificateDTO certificateDTO : dtoList) {
            list.add(toEntity(certificateDTO));
        }

        return list;
    }

    @Override
    public List<CertificateDTO> toDto(List<Certificate> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CertificateDTO> list = new ArrayList<CertificateDTO>(entityList.size());
        for (Certificate certificate : entityList) {
            list.add(toDto(certificate));
        }

        return list;
    }
}
