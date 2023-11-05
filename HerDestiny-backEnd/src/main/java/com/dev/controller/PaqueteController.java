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

import com.dev.domain.Paquete;
import com.dev.service.PaqueteService;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {

    @Autowired
    private PaqueteService paqueteService;

    @PostMapping
    public ResponseEntity<?> crearPaquete(@Valid @RequestBody Paquete paquete, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Manejar errores de validación, como campos incorrectos
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }
        
        Paquete paqueteCreado = paqueteService.crearPaquete(paquete);
        return ResponseEntity.status(HttpStatus.CREATED).body(paqueteCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPaquetePorId(@PathVariable Long id) {
        Paquete paquete = paqueteService.obtenerPaquetePorId(id);
        if (paquete != null) {
            return ResponseEntity.ok(paquete);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Paquete>> obtenerTodosLosPaquetes() {
        List<Paquete> paquetes = paqueteService.obtenerTodosLosPaquetes();
        return ResponseEntity.ok(paquetes);
    }

    @PutMapping
    public ResponseEntity<?> actualizarPaquete(@Valid @RequestBody Paquete paquete, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errores = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errores);
        }

        Paquete paqueteActualizado = paqueteService.actualizarPaquete(paquete);
        if (paqueteActualizado != null) {
            return ResponseEntity.ok(paqueteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaquete(@PathVariable Long id) {
        paqueteService.eliminarPaquete(id);
        return ResponseEntity.noContent().build();
    }
}