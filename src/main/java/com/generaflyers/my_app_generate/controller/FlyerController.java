package com.generaflyers.my_app_generate.controller;

import com.generaflyers.my_app_generate.dto.FlyerDto;
import com.generaflyers.my_app_generate.model.Producto;
import com.generaflyers.my_app_generate.repository.ProductoRepository;
import com.generaflyers.my_app_generate.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flyers")
@CrossOrigin(origins = "*")
public class FlyerController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/generar")
    public ResponseEntity<byte[]> generarFlyer(@RequestBody FlyerDto flyerDto) {

        // 1. Buscamos el producto en la DB usando el ID que nos mandaron
        Producto producto = productoRepository.findById(flyerDto.getProductoId())
                .orElseThrow(() -> new RuntimeException("¡Producto no encontrado! ID: " + flyerDto.getProductoId()));

        // 2. Llamamos al servicio (El "Motor") para crear el PDF
        byte[] pdfBytes = pdfService.generarFlyerPdf(
                producto,
                flyerDto.getTitulo(),
                flyerDto.getPrecio(),
                flyerDto.getFooter()
        );

        // 3. Devolvemos el archivo PDF para que el navegador lo descargue o muestre
        return ResponseEntity.ok()
                // Este header le dice al navegador: "Es un archivo adjunto llamado flyer.pdf"
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=flyer.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}