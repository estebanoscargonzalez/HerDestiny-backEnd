package com.dev.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.domain.Rol;
import com.dev.repository.RolRepository;


@Service
public class RolServiceImpl implements RolService{
	
	@Autowired
	private RolRepository repo;
	
	/**
	 * 
	 */
	@Override
	public Rol registrar(Rol rol) throws Exception {
		
		return repo.save(rol);
	}
	

	/**
	 * 
	 */
	@Override
	public Rol modificar(Rol rol) throws Exception {
		
	    if (Objects.isNull(rol.getIdRol())) {
	        throw new ServiceException("El ID del usuario es nulo, no se puede modificar.");
	    }
	    
	    Optional<Rol> existeRol = repo.findById(rol.getIdRol());
	    
	    if (!existeRol.isPresent()) {
	        throw new ServiceException("El usuario con ID " + rol.getIdRol() + " no existe en la base de datos.");
	    }
	    
	    
	    return repo.save(rol);
	}

	/**
	 * 
	 */
	@Override
	public List<Rol> listarRol() throws Exception {
		
		return repo.findAll();
	}

	
	/**
	 * 
	 */
	@Override
	public Rol buscarPorId(Long id) throws Exception {
		
		return repo.findById(id).orElse(null);
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public void eliminar(Long id) throws Exception {
		repo.deleteById(id);
		
	}

}