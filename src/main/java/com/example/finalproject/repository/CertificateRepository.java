package com.example.finalproject.repository;

import com.example.finalproject.domain.Certificate;
import com.example.finalproject.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Certificate findCertificateByEmployee(String employee);
}
