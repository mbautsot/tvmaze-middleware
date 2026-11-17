# TV Maze Middleware API

API REST desarrollada con **Java 21 y Spring Boot**, diseñada para funcionar como una capa de integración (*middleware*) entre los consumidores del servicio y la API pública de [TV Maze](https://www.tvmaze.com/api).

El servicio centraliza el consumo de información de programas de televisión, implementa una estrategia de caché persistente para optimizar las consultas a la API externa y permite almacenar comentarios y calificaciones de los usuarios mediante **MongoDB Atlas**.

## Arquitectura y tecnologías

El proyecto utiliza las siguientes tecnologías y componentes:

- **Lenguaje:** Java 21.
- **Framework:** Spring Boot.
- **Módulos Spring:** Spring Web, Spring Validation y Spring Data MongoDB.
- **Integración HTTP:** `RestClient` de Spring para la comunicación con la API de TV Maze.
- **Persistencia:** MongoDB Atlas para almacenar información en caché, comentarios y calificaciones.
- **Estrategia de caché:** Patrón *Cache-Aside*, utilizado para consultar primero la información almacenada localmente y recurrir al servicio externo cuando no existe un registro en caché.
- **Manejo de excepciones:** Implementación de @RestControllerAdvice mediante GlobalExceptionHandler, centralizando el tratamiento de errores y proporcionando respuestas HTTP estandarizadas.

### Manejo de errores

La API contempla el manejo centralizado de excepciones para mantener respuestas consistentes ante diferentes escenarios:

- 400 Bad Request: solicitudes con parámetros o datos inválidos.
- 404 Not Found: recursos no encontrados.
- 500 Internal Server Error: errores inesperados durante el procesamiento de las solicitudes.

## Estructura del proyecto

La aplicación sigue una organización por capas, separando las responsabilidades de integración, exposición de endpoints, lógica de negocio y acceso a datos.

src/main/java/com/mbautista/tvmazemiddleware/
│
├── client/          # Integración HTTP con TV Maze (TvMazeClient)
├── config/          # Configuración de componentes y RestClient
├── controller/      # Exposición de endpoints REST
├── dto/             # Objetos de transferencia de datos
│   ├── request/     # Modelos de entrada
│   ├── response/    # Modelos de respuesta
│   └── external/    # Modelos de la API externa
├── exception/       # Excepciones y manejo global de errores
├── model/           # Documentos y modelos de MongoDB
├── repository/      # Acceso a datos mediante Spring Data MongoDB
└── service/         # Lógica de negocio y gestión de caché


## Componentes principales

 Componente - Responsabilidad 

TvMazeClient     -      Encapsula las llamadas HTTP hacia la API externa.
ShowController   -      Expone los endpoints REST para los consumidores. 
ShowService      -      Coordina la lógica de negocio y la estrategia de caché. 
GlobalExceptionHandler  -    Centraliza el manejo de excepciones y respuestas de error. 
ShowCacheDocument       -   Representa la información de programas almacenada en caché. 
ShowCommentDocument     -   Representa los comentarios y calificaciones persistidos en MongoDB. 