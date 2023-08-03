package com.example.finalproject.repository;

import com.example.finalproject.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailIgnoreCase(String email);
    Optional<Employee> findByEmployeeCodeContainingIgnoreCase(String employeeCode);

    Page<Employee> findAllByLastnameOrEmailContainingIgnoreCase(String textSearchName, String textSearchEmail, Pageable pageable);
    Employee findEmployeeByEmail(String email);
    Employee findEmployeeByPositionAndDepartmentId(String position, Long departmentId);
    Long countByDepartmentId(Long departmentId);
    List<Employee> findByDepartmentId(Long departmentId);
}
