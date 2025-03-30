package com.dev.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.domain.Rol;
import com.dev.service.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping("/")
    public ResponseEntity<?> registrarRol(@Valid @RequestBody Rol rol, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, puedes crear un mapa de mensajes de error
            Map<String, String> errores = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errores.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        
        Rol nuevoRol = rolService.registrar(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol);
    }

    @PutMapping("/")
    public ResponseEntity<?> modificarRol(@Valid @RequestBody Rol rol, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, puedes crear un mapa de mensajes de error
            Map<String, String> errores = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errores.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        
        Rol rolModificado = rolService.modificar(rol);
        return ResponseEntity.ok(rolModificado);
    }

    @GetMapping("/")
    public ResponseEntity<List<Rol>> listarRoles() throws Exception {
        List<Rol> roles = rolService.listarRol();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarRolPorId(@PathVariable Long id) throws Exception {
        Rol rol = rolService.buscarPorId(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) throws Exception {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}