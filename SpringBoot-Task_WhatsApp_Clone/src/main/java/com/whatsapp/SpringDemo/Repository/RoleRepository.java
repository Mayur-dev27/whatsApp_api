package com.whatsapp.SpringDemo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whatsapp.SpringDemo.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByRoleName(String roleName);
}
