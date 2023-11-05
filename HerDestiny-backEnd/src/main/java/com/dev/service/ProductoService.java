package com.dev.service;

import java.util.List;

import com.dev.domain.Producto;

public interface ProductoService {
    Producto crearProducto(Producto producto);
    Producto obtenerProductoPorId(Long id);
    List<Producto> obtenerTodosLosProductos();
    Producto actualizarProducto(Producto producto);
    void eliminarProducto(Long id);
}