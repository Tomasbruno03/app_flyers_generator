package com.generaflyers.my_app_generate.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity //Convierte la clase en una tabla MySQL
@Data //La dependencia lombok escribe automaticamente getters, setters y toString
public class producto {

    @Id //Informa que es PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Para que sea autoincremental
    private long Id;

    @NotBlank(message = "El nombre no puede estar Vacio")
    private String nombreProducto;

    @NotBlank(message = "La presentacion no puede ser Vacia")
    private String presentacionProducto;

    @NotBlank(message = "Debe agregar el nombre de la imagen")
    private String nombreImagen;

}
