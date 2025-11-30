package com.generaflyers.my_app_generate.service;

import com.generaflyers.my_app_generate.model.Producto;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service // Importante: Esto le dice a Spring que esta clase contiene lógica!!
public class PdfService {

    public byte[] generarFlyerPdf(Producto producto, String titulo, String precio, String footer) {
        //Crea el "buffer" que escribe el pdf
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            // 2. Creamos el documento PDF
            Document document = new Document();

            // 3. Conectamos el documento con el buffer de salida
            PdfWriter.getInstance(document, outputStream);

            // 4. Abrimos el documento para escribir
            document.open();

            // --- ACÁ EMPIEZA EL DISEÑO (Por ahora solo texto feo) ---
            document.add(new Paragraph(titulo)); // El título de la promo
            document.add(new Paragraph("Producto: " + producto.getNombreProducto()));
            document.add(new Paragraph("Presentación: " + producto.getPresentacionProducto()));
            document.add(new Paragraph("PRECIO: $" + precio));
            document.add(new Paragraph(footer)); // El pie de página

            // 5. Cerramos el documento
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 6. Devolvemos el PDF convertido a bytes (listo para descargar)
        return outputStream.toByteArray();
    }
}