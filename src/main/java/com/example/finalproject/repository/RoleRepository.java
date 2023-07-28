package com.example.finalproject.repository;

import com.example.finalproject.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String id);

}
