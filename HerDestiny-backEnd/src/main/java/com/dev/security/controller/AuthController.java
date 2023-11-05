package com.dev.security.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.dev.domain.Rol;
import com.dev.domain.User;
import com.dev.security.JwtProvider;
import com.dev.security.dto.JwtDTO;
import com.dev.security.dto.LoginUsuario;
import com.dev.service.RolService;
import com.dev.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private  UserService usuarioService;

    @Autowired
    private  RolService rolService;

    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/nuevoUsuario")
    public ResponseEntity<?> nuevo(@Valid @RequestBody User user, BindingResult bindingResult) throws Exception {


        Map<String, String> errores = new HashMap<>();
        
        // Validaci�n de errores del modelo.
        if(bindingResult.hasErrors()){
            for (FieldError error : bindingResult.getFieldErrors()) {
                errores.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(Collections.singletonMap("errores", bindingResult.getFieldErrors()));
        }

        try {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<Rol> roles = new ArrayList<>() ;

        for (Rol rol : user.getRoles()) {

            roles.add(rolService.buscarPorId(rol.getIdRol()));

        }

        user.setRoles(roles);
  
         usuarioService.registrar(user);
        return  ResponseEntity.status(HttpStatus.CREATED).body(user);
        
        } catch (ServiceException e) {
            errores.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
        catch (Exception e) {
            errores.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
        

    }


    /**
     * modificar
     * @param usuarioDTO
     * @return
     * @throws Exception
     */
    @PutMapping("/modificar")
    public ResponseEntity<?> modificar(@RequestBody User user) throws Exception {
    	
    	try {
        	User usuarioModificado = usuarioService.modificar(user);
            return ResponseEntity.ok(usuarioModificado);
		} catch (Exception e) {
			
	            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
		}

    }



    /**
     * Listar usuarios
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> listarUsuarios(
        @RequestParam(value = "username", required = false) String username,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "email", required = false) String email) {
        try {
            List<User> users = usuarioService.listarUsuarios(username, name, email);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    


    /**
     * eliminar
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * buscar por id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Long id) {
        try {
        	User usuario = usuarioService.buscarPorId(id);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    
    /**
     * Login
     * @param loginUsuario
     * @param bindingResult
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>("Campos mal puestos.", HttpStatus.BAD_REQUEST);
//        }
//        
    	Map<String, String> errores = new HashMap<>();
        if(bindingResult.hasErrors()){
            for (FieldError error : bindingResult.getFieldErrors()) {
                errores.put(error.getField(), error.getDefaultMessage());
            }
            log.error(" error: " + bindingResult.getFieldErrors());
           
            return ResponseEntity.badRequest().body(Collections.singletonMap("errores", bindingResult.getFieldErrors()));
        }

        User usuario = usuarioService.obtenerUsuario(loginUsuario.getUsername()).orElse(null);

        if (usuario == null) {
            return new ResponseEntity<>("El usuario no existe.", HttpStatus.UNAUTHORIZED);
        }  else {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(), loginUsuario.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            JwtDTO jwtDto = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
            return new ResponseEntity<>(jwtDto, HttpStatus.OK);
        }
    }

    
    @GetMapping("/usuario")
    public ResponseEntity<List<Rol>> obtenerRolesUsuario() {
        try {
            List<Rol> roles = usuarioService.obtenerRolesUsuario();
            return ResponseEntity.ok(roles);
        } catch (ServiceException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}