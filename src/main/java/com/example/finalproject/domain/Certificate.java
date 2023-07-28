package com.example.finalproject.domain;

import com.example.finalproject.service.dto.EmployeeDTO;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_certificate", nullable = false, unique = true)
    private String nameCertificate;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "certification_officer")
    private String certificationOfficer;

    public Certificate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCertificate() {
        return nameCertificate;
    }

    public void setNameCertificate(String nameCertificate) {
        this.nameCertificate = nameCertificate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getCertificationOfficer() {
        return certificationOfficer;
    }

    public void setCertificationOfficer(String certificationOfficer) {
        this.certificationOfficer = certificationOfficer;
    }
}
