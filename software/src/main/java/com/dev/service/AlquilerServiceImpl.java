package com.dev.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dev.domain.Alquiler;
import com.dev.domain.Correo;
import com.dev.domain.Producto;
import com.dev.domain.UsuarioPrincipal;
import com.dev.repository.AlquilerRepository;
import com.dev.repository.ProductoRepository;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;
    
    @Autowired
    private CorreoService correoService;
    
    @Autowired
    private ProductoRepository productoRepo;

    @Override
    @Transactional
    public Alquiler crearAlquiler(Alquiler alquiler) {
        try {
            alquiler.setFcAlquiler(LocalDateTime.now());
            Alquiler alquilerGuardado = alquilerRepository.save(alquiler);
            UsuarioPrincipal usuario = getUsuarioPrincipalAutenticado();
            
            // Busca el producto asociado
            Producto producto = productoRepo.findById(alquilerGuardado.getProducto().getIdProducto()).orElse(null);
            if (producto == null) {
                throw new RuntimeException("Producto no encontrado para el ID proporcionado");
            }
            
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("nombre", usuario.getName());            
            
            modelo.put("producto", producto); 
            
            Correo correo = new Correo("correo_empresa@example.com", usuario.getEmail(), "Confirmación de Alquiler", modelo);
            correoService.enviarCorreo(correo);
            
            return alquilerGuardado;
        } catch (Exception e) {          
            throw new RuntimeException("Error al crear el alquiler", e);
        }
    }


    @Override
    public Alquiler obtenerAlquilerPorId(Long id) {
        return alquilerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Alquiler> obtenerTodosLosAlquileres() {
        return alquilerRepository.findAll();
    }

    @Override
    public Alquiler actualizarAlquiler(Alquiler alquiler) {
 
        return alquilerRepository.save(alquiler);
    }

    @Override
    public void eliminarAlquiler(Long id) {
        alquilerRepository.deleteById(id);
    }
    
    
    private UsuarioPrincipal getUsuarioPrincipalAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UsuarioPrincipal) {
            return (UsuarioPrincipal) authentication.getPrincipal();
        } else {
            throw new ServiceException("No se pudo obtener el usuario autenticado.");
        }
    }
}