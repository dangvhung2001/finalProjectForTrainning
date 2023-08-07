package com.example.finalproject.repository;

import com.example.finalproject.domain.Department;
import com.example.finalproject.domain.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    Page<Department> findAllByNameContainingIgnoreCase(String textSearchName, Pageable pageable);

    Optional<Department> findFirstByDepartmentCodeContainingIgnoreCase(String departmentCode);

    Optional<Department> findFirstByNameContainingIgnoreCase(String name);


}
