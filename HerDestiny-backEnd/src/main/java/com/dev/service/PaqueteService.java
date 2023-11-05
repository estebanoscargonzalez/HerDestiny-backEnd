package com.dev.service;

import java.util.List;

import com.dev.domain.Paquete;

public interface PaqueteService {
	
    Paquete crearPaquete(Paquete paquete);
    Paquete obtenerPaquetePorId(Long id);
    List<Paquete> obtenerTodosLosPaquetes();
    Paquete actualizarPaquete(Paquete paquete);
    void eliminarPaquete(Long id);

}