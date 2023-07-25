package com.example.finalproject.service.dto;

import java.sql.Date;

public class CertificateDTO {
    private Long id;
    private String nameCertificate;
    private Date issueDate;
    private Date expirationDate;
    private String description;
    private EmployeeDTO employee;
    private String certificationOfficer;

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

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public String getCertificationOfficer() {
        return certificationOfficer;
    }

    public void setCertificationOfficer(String certificationOfficer) {
        this.certificationOfficer = certificationOfficer;
    }
}
