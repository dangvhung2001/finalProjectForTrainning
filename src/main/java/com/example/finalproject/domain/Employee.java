package com.example.finalproject.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;
import java.sql.Date;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "img_url", length = 255)
    private String imgUrl;

    @Column(name = "firstname", nullable = false, length = 255)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 255)
    private String lastname;

    @Column(nullable = false, length = 255)
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @Column(name = "create_at")
    private Date createdAt;

    @Column
    private Byte sex;

    @Column(name = "marital_status", length = 255)
    private String maritalStatus;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "department_id")
    private Long departmentId;
    private String citizenCode;

    private Date issueDate;

    private String placeOfIssue;

    private String employeeCode;
    @Email
    private String email;

    private String address;
    @Min(value = 0, message = "Số điện thoại phải là số không âm")
    private Integer phone;

    private Date startDate;

    @Column(name = "salary_coefficient")
    @Min(value = 0, message = "Giá trị không thể nhỏ hơn 0")
    private Float salaryCoefficient;

    @Column
    @Min(value = 0, message = "Giá trị không thể nhỏ hơn 0")
    private Float salary;

    @Column(length = 255)
    private String position;

    @Column(name = "education_level", length = 255)
    private String educationLevel;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Department department;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_role",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_name")})
    private Set<Role> roles = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_project",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public void setSalaryCoefficient(Float salaryCoefficient) {
        this.salaryCoefficient = salaryCoefficient;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCitizenCode() {
        return citizenCode;
    }

    public void setCitizenCode(String citizenCode) {
        this.citizenCode = citizenCode;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public float getSalaryCoefficient() {
        return salaryCoefficient;
    }

    public void setSalaryCoefficient(float salaryCoefficient) {
        this.salaryCoefficient = salaryCoefficient;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}