package com.tarento.sec.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tarento.sec.model.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
