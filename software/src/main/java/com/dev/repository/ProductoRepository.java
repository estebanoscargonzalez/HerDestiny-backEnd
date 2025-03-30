package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.domain.Producto;

public interface ProductoRepository  extends JpaRepository<Producto, Long> {

}