package com.dev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.domain.Paquete;
import com.dev.repository.PaqueteRepository;

@Service
public class PaqueteServiceImpl implements PaqueteService {

    @Autowired
    private PaqueteRepository paqueteRepository;

    @Override
    public Paquete crearPaquete(Paquete paquete) {
        return paqueteRepository.save(paquete);
    }

    @Override
    public Paquete obtenerPaquetePorId(Long id) {
        return paqueteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Paquete> obtenerTodosLosPaquetes() {
        return paqueteRepository.findAll();
    }

    @Override
    public Paquete actualizarPaquete( Paquete paquete) {

        return paqueteRepository.save(paquete);
    }

    @Override
    public void eliminarPaquete(Long id) {
        paqueteRepository.deleteById(id);
    }
}