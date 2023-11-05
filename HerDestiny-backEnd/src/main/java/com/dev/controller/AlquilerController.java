package com.dev.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.domain.Alquiler;
import com.dev.service.AlquilerService;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @PostMapping
    public ResponseEntity<?> crearAlquiler(@Valid @RequestBody Alquiler alquiler, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        
        
        Alquiler alquilerCreado = alquilerService.crearAlquiler(alquiler);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquilerCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAlquiler(@PathVariable Long id) {
        Alquiler alquiler = alquilerService.obtenerAlquilerPorId(id);
        if (alquiler == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alquiler);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAlquiler(@Valid @RequestBody Alquiler alquiler,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        

        Alquiler alquilerActualizado = alquilerService.actualizarAlquiler(alquiler);
        return ResponseEntity.ok(alquilerActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlquiler(@PathVariable Long id) {
        alquilerService.eliminarAlquiler(id);
        return ResponseEntity.noContent().build();
    }

    // Otros métodos del controlador para listar, buscar, etc.

}