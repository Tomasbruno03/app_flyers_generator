Generador de Ofertas

De tardar 30 minutos a 5 segundos: Cómo automaticé la creación de cartelería para mi trabajo usando Java y Spring Boot.

<img width="1646" height="850" alt="image" src="https://github.com/user-attachments/assets/729c9057-002a-4437-8f89-c3c86cb2a56b" />

El Problema

Tenía un problema recurrente que me consumía mucho tiempo operativo: la actualización constante de precios.

Cada vez que tenía que sacar una oferta, perdía media hora diseñando el flyer manual, acomodando el logo, buscando la foto del producto y verificando precios. Si tenía que hacer 10 ofertas, perdía la mañana entera en tareas repetitivas.

Como estudio Ingeniería en Sistemas, decidí automatizar este proceso.

La Solución

Desarrollé un sistema web full stack que centraliza la gestión. La aplicación permite:

Gestionar Productos: Cargar una vez la foto y el nombre (Coca Cola, Mayonesa, etc.) para armar un catálogo.

Búsqueda Rápida: Un buscador en tiempo real para encontrar el producto al instante.

Generación Automática: Ingreso el precio actual y el sistema genera un PDF listo para imprimir en menos de 1 segundo, respetando siempre la identidad de marca (colores, logos y tipografías).

Resultado: Reduje el tiempo de producción de 30 minutos a 5 segundos por cartel.

Tecnologías Utilizadas

Elegí un stack robusto y clásico para profundizar conocimientos en arquitectura Enterprise.

Backend: Java 17 + Spring Boot 3.

Base de Datos: MySQL con Hibernate/JPA.

Generación de PDF: OpenPDF (con lógica de dibujo vectorial para diseño responsivo).

Frontend: HTML5, JavaScript (Vanilla) y Tailwind CSS.

Almacenamiento: Gestión de archivos locales para las imágenes.

Funcionalidades

Live Search: Búsqueda asíncrona de productos mientras se escribe.

Diseño Responsivo en PDF: Algoritmo que ajusta el tamaño de la letra del título automáticamente si el texto es muy largo, evitando que se rompa el diseño.

Capas de Diseño: El generador de PDF trabaja por capas (background, assets, texto) para asegurar que ningún elemento tape a otro.

CRUD Completo: Alta, Baja y Modificación de productos con soporte para subida de imágenes.

Capturas de Pantalla

El Generador
<img width="1198" height="697" alt="image" src="https://github.com/user-attachments/assets/ca58cce8-6c7a-4b79-b0ba-65375c99771e" />
<img width="1208" height="630" alt="image" src="https://github.com/user-attachments/assets/d2d63964-3d01-4198-a51e-1fd74465572e" />
<img width="1222" height="758" alt="image" src="https://github.com/user-attachments/assets/d7686329-9460-47ad-ae5c-11d9f5fc5327" />


Cargando un Producto
<img width="879" height="727" alt="image" src="https://github.com/user-attachments/assets/4d56deb9-54a8-4c56-9e3f-559a37b57371" />


Modificando un producto
<img width="1235" height="648" alt="image" src="https://github.com/user-attachments/assets/ca354c6e-00e3-47fe-8f55-98f7420a01cc" />
<img width="813" height="768" alt="image" src="https://github.com/user-attachments/assets/c6e7336b-233d-49c4-9174-ac2afd4bef0e" />



Instalación

Si querés probar el código localmente:

Requisitos: Java 17 y MySQL.

Crear base de datos: CREATE DATABASE db_flyers;.

Configurar usuario/pass en application.properties.

Ejecutar: mvn spring-boot:run.

Acceder a: http://localhost:8080.

Desarrollado por Tomas Bruno
Estudiante de Ingeniería en Sistemas
https://www.linkedin.com/in/tomas-bruno-ba92a2210/
