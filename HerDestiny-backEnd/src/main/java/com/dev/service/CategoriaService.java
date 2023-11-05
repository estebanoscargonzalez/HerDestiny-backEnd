package com.dev.service;

import java.util.List;

import com.dev.domain.Categoria;

public interface CategoriaService {

	   Categoria guardarCategoria(Categoria categoria);
	    List<Categoria> obtenerTodasCategorias();
	    Categoria obtenerCategoriaPorId(Long idCategoria);
	    void eliminarCategoria(Long idCategoria);
}