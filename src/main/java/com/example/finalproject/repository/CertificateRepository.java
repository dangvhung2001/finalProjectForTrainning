package com.example.finalproject.repository;

import com.example.finalproject.domain.Certificate;
import com.example.finalproject.domain.Skill;
import com.example.finalproject.service.dto.CertificateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByEmployeeId(Long employeeId);
}
