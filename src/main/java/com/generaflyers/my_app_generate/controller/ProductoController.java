package com.generaflyers.my_app_generate.controller;

import com.generaflyers.my_app_generate.model.Producto;
import com.generaflyers.my_app_generate.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Para responder con status HTTP (200, 400, etc)
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Definimos dónde se guardan las fotos
    private static final String RUTA_UPLOADS = "uploads";

    // --- ENDPOINT POST: Recibe datos y archivo ---
    @PostMapping
    public ResponseEntity<?> crearProducto(
            @RequestParam("nombre") String nombre,
            @RequestParam("presentacion") String presentacion,
            @RequestParam("imagen") MultipartFile archivoImagen // El archivo real
    ) {
        // 1. VALIDACIÓN MANUAL (Para mantener la robustez)
        if (nombre.isEmpty() || presentacion.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El nombre y la presentación son obligatorios.");
        }
        if (archivoImagen.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Debes subir una imagen.");
        }

        try {
            // 2. LÓGICA DE GUARDADO DE IMAGEN
            // Generamos nombre único: "tiempo_nombreOriginal.jpg" (ej: 178234_coca.jpg)
            // Esto evita que si subís dos fotos con el mismo nombre, se pisen.
            String nombreArchivo = System.currentTimeMillis() + "_" + archivoImagen.getOriginalFilename();

            // Creamos la ruta destino y copiamos los bytes del archivo ahí
            Path rutaCompleta = Paths.get(RUTA_UPLOADS).resolve(nombreArchivo).toAbsolutePath();
            Files.copy(archivoImagen.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

            // 3. GUARDADO EN BASE DE DATOS
            Producto nuevoProducto = new Producto();
            nuevoProducto.setNombreProducto(nombre);
            nuevoProducto.setPresentacionProducto(presentacion);
            nuevoProducto.setNombreImagen(nombreArchivo); // Guardamos solo el nombre del archivo

            Producto productoGuardado = productoRepository.save(nuevoProducto);

            // Respondemos con el producto creado (Status 200 OK)
            return ResponseEntity.ok(productoGuardado);

        } catch (IOException e) {
            // Si falla el disco duro o la carpeta, devolvemos error 500
            return ResponseEntity.internalServerError().body("Error al subir la imagen: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/buscar")
    public List<Producto> buscarProductos(@RequestParam String nombre) {
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombre);
    }

    // --- ENDPOINT 4: ACTUALIZAR PRODUCTO (PUT) ---
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("presentacion") String presentacion,
            @RequestParam(value = "imagen", required = false) MultipartFile archivoImagen // Es false porque quizás no querés cambiar la foto
    ) {
        // 1. Buscamos si existe
        Producto productoExistente = productoRepository.findById(id).orElse(null);
        if (productoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // 2. Actualizamos datos de texto
            productoExistente.setNombreProducto(nombre);
            productoExistente.setPresentacionProducto(presentacion);

            // 3. Lógica de la imagen: Solo la cambiamos si el usuario mandó una nueva
            if (archivoImagen != null && !archivoImagen.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + archivoImagen.getOriginalFilename();
                Path rutaCompleta = Paths.get(RUTA_UPLOADS).resolve(nombreArchivo).toAbsolutePath();
                Files.copy(archivoImagen.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

                // Actualizamos el nombre en la BD
                productoExistente.setNombreImagen(nombreArchivo);
            }

            // 4. Guardamos los cambios
            Producto productoActualizado = productoRepository.save(productoExistente);
            return ResponseEntity.ok(productoActualizado);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al procesar la imagen");
        }
    }

    // --- ENDPOINT 5: ELIMINAR PRODUCTO (DELETE) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        // 1. Buscamos si existe
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // 2. Borramos de la base de datos
        // (Opcional: Acá podrías agregar código para borrar también el archivo físico de la carpeta uploads, pero por ahora dejémoslo simple)
        productoRepository.deleteById(id);

        return ResponseEntity.ok().body("Producto eliminado correctamente");
    }
}