package com.example.finalproject.repository;

import com.example.finalproject.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findSkillByEmployee (String employee);
}
