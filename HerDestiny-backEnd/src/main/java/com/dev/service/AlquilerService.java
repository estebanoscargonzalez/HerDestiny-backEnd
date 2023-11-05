package com.dev.service;

import java.util.List;

import com.dev.domain.Alquiler;

public interface AlquilerService {
	
	   Alquiler crearAlquiler(Alquiler alquiler);
	    Alquiler obtenerAlquilerPorId(Long id);
	    List<Alquiler> obtenerTodosLosAlquileres();
	    Alquiler actualizarAlquiler(Alquiler alquiler);
	    void eliminarAlquiler(Long id);

}