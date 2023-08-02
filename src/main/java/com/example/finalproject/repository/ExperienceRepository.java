package com.example.finalproject.repository;

import com.example.finalproject.domain.Department;
import com.example.finalproject.domain.Experience;
import com.example.finalproject.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long>, JpaSpecificationExecutor<Experience> {

    Optional<Experience> findByNameExperienceContainingIgnoreCase(String name);

}
