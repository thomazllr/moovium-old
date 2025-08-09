package com.thomazllr.moovium.repository;

import com.thomazllr.moovium.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByNameIgnoreCase(String name);
}
