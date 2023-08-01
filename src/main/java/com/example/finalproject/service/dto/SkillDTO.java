package com.example.finalproject.service.dto;

public class SkillDTO {
    private Long id;
    private String name;
    private int level;
    private int yearExperience;
    private int monthExperience;
    private String description;
    private EmployeeDTO employee;

    private int languageUsageCount;

    public SkillDTO() {
    }

    public SkillDTO(Long id, String name, int level, int yearExperience, int monthExperience, String description, EmployeeDTO employee) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.yearExperience = yearExperience;
        this.monthExperience = monthExperience;
        this.description = description;
        this.employee = employee;
    }

    public SkillDTO(String name, int languageUsageCount) {
        this.name = name;
        this.languageUsageCount = languageUsageCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getYearExperience() {
        return yearExperience;
    }

    public void setYearExperience(int yearExperience) {
        this.yearExperience = yearExperience;
    }

    public int getMonthExperience() {
        return monthExperience;
    }

    public void setMonthExperience(int monthExperience) {
        this.monthExperience = monthExperience;
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

    public int getLanguageUsageCount() {
        return languageUsageCount;
    }

    public void setLanguageUsageCount(int languageUsageCount) {
        this.languageUsageCount = languageUsageCount;
    }
}
