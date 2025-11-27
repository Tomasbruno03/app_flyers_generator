package com.generaflyers.my_app_generate.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity //Convierte la clase en una tabla MySQL
@Data //La dependencia lombok escribe automaticamente getters, setters y toString
public class producto {

    @Id //Informa que es PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para que sea autoincremental
    private long Id;

    private String nombreProducto;
    private String presentacionProducto;
    private String nombreImagen;

}
