package com.dev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.domain.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByRolNombre(String rolNombre);
    
}