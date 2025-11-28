package com.generaflyers.my_app_generate.repository;


import com.generaflyers.my_app_generate.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //Jpa Repository tiene los metodos del CRUD
}
