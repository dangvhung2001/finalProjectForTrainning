package com.example.finalproject.service.dto;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

public class ExperienceDTO {
    private Long id;
    @NotEmpty(message = "Tên kinh nghiệm không được để trống")

    private String name_experience;
    private Date timeStart;
    private Date timeEnd;
    private String language;
    private String link;
    private String workplace;
    private String position;
    private int teamSize;
    private String description;
    private String os;
    private String framework;
    private EmployeeDTO employee;

    public ExperienceDTO() {
    }

    public ExperienceDTO(Long id, String name_experience, Date timeStart, Date timeEnd, String language, String link, String workplace, String position, int teamSize, String description, String os, String framework, EmployeeDTO employee) {
        this.id = id;
        this.name_experience = name_experience;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.language = language;
        this.link = link;
        this.workplace = workplace;
        this.position = position;
        this.teamSize = teamSize;
        this.description = description;
        this.os = os;
        this.framework = framework;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public String getName_experience() {
        return name_experience;
    }

    public void setName_experience(String name_experience) {
        this.name_experience = name_experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }
}
