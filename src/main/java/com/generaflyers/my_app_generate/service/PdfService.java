package com.generaflyers.my_app_generate.service;

import com.generaflyers.my_app_generate.model.Producto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfService {

    private static final String RUTA_UPLOADS = "uploads";

    // Colores
    private static final Color COLOR_ROJO_NINI = new Color(185, 15, 25);
    private static final Color COLOR_AZUL_NINI = new Color(10, 20, 90);
    private static final Color COLOR_CREMA_FONDO = new Color(252, 250, 242);

    public byte[] generarFlyerPdf(Producto producto, String titulo, String precio, String footer) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A6, 0, 0, 0, 0);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            PdfContentByte canvas = writer.getDirectContent();
            float ancho = document.getPageSize().getWidth();
            float alto = document.getPageSize().getHeight();
            float centroX = ancho / 2;

            // --- ZONAS SEGURAS ---
            float altoCabeceraRoja = 60f;
            float altoPieAzul = 40f;
            float pisoImagen = 145f;
            float techoImagen = alto - altoCabeceraRoja - 10;

            // --- CAPAS DE FONDO Y LOGO (Igual que antes) ---
            canvas.saveState();
            canvas.setColorFill(COLOR_CREMA_FONDO);
            canvas.rectangle(0, 0, ancho, alto);
            canvas.fill();
            canvas.restoreState();

            canvas.saveState();
            canvas.setColorFill(COLOR_ROJO_NINI);
            canvas.rectangle(0, alto - altoCabeceraRoja, ancho, altoCabeceraRoja);
            canvas.fill();
            canvas.restoreState();

            canvas.saveState();
            canvas.setColorFill(COLOR_AZUL_NINI);
            canvas.rectangle(0, 0, ancho, altoPieAzul);
            canvas.fill();
            canvas.restoreState();

            // LOGO
            try {
                ClassPathResource logoResource = new ClassPathResource("static/img/logo.png");
                Image logo = Image.getInstance(logoResource.getURL());
                logo.scaleToFit(50, 40);
                logo.setAbsolutePosition(ancho - logo.getScaledWidth() - 5, alto - (altoCabeceraRoja / 2) - (logo.getScaledHeight() / 2));
                canvas.addImage(logo);
            } catch (Exception e) { }

            // TÍTULO
            float espacioParaLogo = 60f;
            dibujarTituloAutoajustable(canvas, titulo.toUpperCase(), 18, ancho - espacioParaLogo - 15, 15, alto - (altoCabeceraRoja / 2) - 6);

            // IMAGEN
            try {
                Path rutaPath = Paths.get(RUTA_UPLOADS).resolve(producto.getNombreImagen()).toAbsolutePath();
                File archivoFoto = new File(rutaPath.toString());
                if (archivoFoto.exists()) {
                    Image imagen = Image.getInstance(rutaPath.toString());
                    float alturaDisponible = techoImagen - pisoImagen;
                    imagen.scaleToFit(240f, alturaDisponible);
                    float xImg = (ancho - imagen.getScaledWidth()) / 2;
                    float espacioSobrante = alturaDisponible - imagen.getScaledHeight();
                    float yImg = pisoImagen + (espacioSobrante / 2);
                    imagen.setAbsolutePosition(xImg, yImg);
                    canvas.addImage(imagen);
                }
            } catch (Exception e) { }

            // --- TEXTOS INFERIORES ---
            escribirTextoAlineado(canvas, producto.getNombreProducto().toUpperCase(), 16, COLOR_AZUL_NINI, centroX, 125, Element.ALIGN_CENTER);
            escribirTextoAlineado(canvas, producto.getPresentacionProducto(), 11, Color.DARK_GRAY, centroX, 105, Element.ALIGN_CENTER);

            // === ACÁ ESTÁ LA MAGIA DEL PRECIO ===
            try {
                // 1. Cargamos la fuente externa (precio.ttf)
                ClassPathResource fontResource = new ClassPathResource("static/fonts/precio.ttf");
                BaseFont bfCustom = BaseFont.createFont(fontResource.getURL().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

                // 2. Creamos la fuente con tamaño GIGANTE (60)
                Font fuentePrecioCustom = new Font(bfCustom, 60, Font.NORMAL, COLOR_ROJO_NINI);

                // 3. Escribimos usando esta fuente nueva
                Phrase frasePrecio = new Phrase("$" + precio, fuentePrecioCustom);
                ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, frasePrecio, centroX, 55, 0); // Ajusté un poco la altura (55)

            } catch (Exception e) {
                // Si falla (no encontró el archivo), usa la vieja fea de respaldo
                System.err.println("No se pudo cargar la fuente custom: " + e.getMessage());
                escribirTextoAlineado(canvas, "$" + precio, 52, COLOR_ROJO_NINI, centroX, 60, Element.ALIGN_CENTER);
            }

            // FOOTER
            float yFooter = altoPieAzul / 2 - 4;
            escribirTextoAlineado(canvas, footer, 10, Color.WHITE, centroX, yFooter, Element.ALIGN_CENTER);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();
    }
    // --- MÉTODOS AUXILIARES (Sin cambios) ---
    private void dibujarTituloAutoajustable(PdfContentByte canvas, String texto, float tamanoMaximo, float anchoDisponible, float x, float y) {
        try {
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            float tamanoActual = tamanoMaximo;
            float anchoTexto = bf.getWidthPoint(texto, tamanoActual);
            while (anchoTexto > anchoDisponible && tamanoActual > 8) {
                tamanoActual -= 1;
                anchoTexto = bf.getWidthPoint(texto, tamanoActual);
            }
            Font fuenteFinal = new Font(Font.HELVETICA, tamanoActual, Font.BOLD, Color.WHITE);
            Phrase frase = new Phrase(texto, fuenteFinal);
            ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, frase, x, y, 0);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void escribirTextoAlineado(PdfContentByte canvas, String texto, float tamano, Color color, float x, float y, int alineacion) {
        try {
            Font fuente = new Font(Font.HELVETICA, tamano, Font.BOLD, color);
            if (color.equals(Color.DARK_GRAY)) {
                fuente = new Font(Font.HELVETICA, tamano, Font.NORMAL, color);
            }
            Phrase frase = new Phrase(texto, fuente);
            ColumnText.showTextAligned(canvas, alineacion, frase, x, y, 0);
        } catch (Exception e) { e.printStackTrace(); }
    }
}