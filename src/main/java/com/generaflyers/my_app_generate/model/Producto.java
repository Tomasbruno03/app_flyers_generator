package com.generaflyers.my_app_generate.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;


@Entity //Convierte la clase en una tabla MySQL

public class Producto {

    @Id //Informa que es PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para que sea autoincremental
    private long id;

    @NotBlank(message = "El nombre no puede estar Vacio")
    private String nombreProducto;

    @NotBlank(message = "La presentacion no puede ser Vacia")
    private String presentacionProducto;

    @NotBlank(message = "Debe agregar el nombre de la imagen")
    private String nombreImagen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public @NotBlank(message = "El nombre no puede estar Vacio") String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(@NotBlank(message = "El nombre no puede estar Vacio") String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public @NotBlank(message = "La presentacion no puede ser Vacia") String getPresentacionProducto() {
        return presentacionProducto;
    }

    public void setPresentacionProducto(@NotBlank(message = "La presentacion no puede ser Vacia") String presentacionProducto) {
        this.presentacionProducto = presentacionProducto;
    }

    public @NotBlank(message = "Debe agregar el nombre de la imagen") String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(@NotBlank(message = "Debe agregar el nombre de la imagen") String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }
}
