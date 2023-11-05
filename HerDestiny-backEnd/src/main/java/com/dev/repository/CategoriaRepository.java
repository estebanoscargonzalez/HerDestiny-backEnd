package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}