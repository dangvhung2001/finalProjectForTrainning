package com.example.finalproject.repository;

import com.example.finalproject.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findOneByEmailIgnoreCase(String email);

    Page<Employee> findAllByLastnameOrEmailContainingIgnoreCase(String textSearchName, String textSearchEmail, Pageable pageable);

    Employee findEmployeeByEmail(String email);
}
