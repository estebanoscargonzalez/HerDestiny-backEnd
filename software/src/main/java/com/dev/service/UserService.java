package com.dev.service;

import java.util.List;
import java.util.Optional;

import com.dev.domain.Rol;
import com.dev.domain.User;

public interface UserService {
	
	
 /**
  * m�todo para validar si existe el correo.
  * @param email
  * @return
  */
    boolean existeCorreo(String email);

	/**
	 * M�todo registrar
	 * @param user
	 * @return
	 * @throws Exception
	 */
	    User registrar(User user) throws Exception;

    /**
     * m�todo para modificar usuarios
     * @param usuarioDTO
     * @return
     * @throws Exception
     */
    User modificar(User user) throws Exception;

    /**
     * m�todo para listar usuarios
     * @return
     * @throws Exception
     */
    List<User> listarUsuarios(String username, String name, String email) throws Exception;

    List<Rol> obtenerRolesUsuario();


    /**
     * buscar por id
     * @param id
     * @return
     * @throws Exception
     */
    User buscarPorId(Long id) throws Exception;



    /**
     * eliminar
     * @param id
     * @throws Exception
     */
    void eliminar(Long id) throws Exception;
    
    /**
     * 
     * @param username
     * @return
     */
    Optional<User> obtenerUsuario(String username);
    
    
    boolean existeUsuario(String username);
    
    /**
     * Obtiene un usuario por su nombre de usuario o direcci�n de correo electr�nico.
     *
     * @param usernameOrEmail Nombre de usuario o direcci�n de correo electr�nico
     * @return Usuario encontrado (si existe)
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

}