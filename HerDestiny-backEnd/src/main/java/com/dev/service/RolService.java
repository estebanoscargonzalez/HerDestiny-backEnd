package com.dev.service;

import java.util.List;

import com.dev.domain.Rol;

public interface RolService {

	
	/**
	 * método para registrar rol
	 * @param rol
	 * @return rol
	 * @throws Exception
	 */

    Rol registrar(Rol rol) throws Exception;

    /**
     * modificar roles
     * @param rol
     * @return
     * @throws Exception
     */
    Rol modificar(Rol rol) throws Exception;

    /**
     * listar roles
     * @return
     * @throws Exception
     */
    List<Rol> listarRol() throws Exception;

    /**
     * buscar por id
     * @param id
     * @return
     * @throws Exception
     */
    Rol buscarPorId(Long id) throws Exception;

    /**
     * eliminar
     * @param id
     * @throws Exception
     */
    void eliminar(Long id) throws Exception;
}