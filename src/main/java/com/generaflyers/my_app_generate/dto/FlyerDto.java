package com.generaflyers.my_app_generate.dto;

public class FlyerDto {
    private Long productoId; // ID del producto que queremos usar (ej: 1)
    private String titulo;   //
    private String precio;   // "1500"
    private String footer;   // "


    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }
    public String getFooter() { return footer; }
    public void setFooter(String footer) { this.footer = footer; }
}