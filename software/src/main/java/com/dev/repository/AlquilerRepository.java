package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.domain.Alquiler;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
}