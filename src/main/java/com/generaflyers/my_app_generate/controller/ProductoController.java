package com.generaflyers.my_app_generate.controller;

import com.generaflyers.my_app_generate.model.producto;
import com.generaflyers.my_app_generate.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") //Acepta peticiones de cualquier lado
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // --- ENDPOINT 1: Guardar un producto nuevo (POST) ---
    @PostMapping
    public producto crearProducto(@Valid @RequestBody producto producto) {
        return productoRepository.save(producto);
    }

    // --- ENDPOINT 2: Traer todos los productos (GET) ---
    @GetMapping
    public List<producto> listarProductos() {
        return productoRepository.findAll();
    }
}