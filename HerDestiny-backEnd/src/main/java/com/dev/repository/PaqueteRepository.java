package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.domain.Paquete;



public interface PaqueteRepository extends JpaRepository<Paquete, Long> {
	
}