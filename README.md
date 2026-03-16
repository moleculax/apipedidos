# 📦 API Pedidos (Spring Boot + PostgreSQL)

Sistema de gestión de pedidos desarrollado con **Java** y **Spring Boot**, utilizando **PostgreSQL** como motor de base de datos relacional.

## Tecnologías utilizadas
*   **Java 25+**
*   **Spring Boot 4.x**
*   **PostgreSQL**: Base de datos principal.
*   **Spring Data JPA**: ORM para persistencia y mapeo de entidades.
*   **Maven**: Gestión de dependencias y ciclo de vida del proyecto.
*   **Lombok**: Reducción de código repetitivo (Boilerplate).

##  Configuración e Instalación

### 1. Requisitos previos
*   Tener instalado **JDK 25** o superior.
*   Instancia de **PostgreSQL 17** activa.
*   Crear una base de datos vacía (ejemplo: `dbpedidos`).

### 2. Configuración de Base de Datos
Asegúrate de que tu archivo `src/main/resources/application.properties` contenga la configuración correcta:

# Limpiar y compilar el proyecto
mvn clean install
# Ejecutar la aplicación
mvn spring-boot:run

---

🔗 **Autor:** [https://moleculaxapp.vercel.app/](https://moleculaxapp.vercel.app/)

**Licencia:** MIT
