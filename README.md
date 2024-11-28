# Med Voll API

## Description
The **Med Voll API** is a robust Java-based application powered by Spring Boot designed to manage a medical system efficiently. The application offers CRUD operations for managing doctors, addresses, and specialties, complete with validations and global exception handling. With its modular and service-oriented architecture, the API is designed for scalability, maintainability, and seamless integration with external systems.

### Core Capabilities:
- **Doctor Management**: Handles creation, updates, logical deletions, and listing of doctors with validations for essential fields such as email, phone, and specialty.
- **Address Management**: Embedded support for managing address information, ensuring data integrity through structured validation.
- **Specialty Management**: Leverages enums to manage doctor specialties, ensuring consistent and validated data stored in the database.
- **Global Exception Handling**: Provides structured error responses for database, validation, and runtime exceptions, improving API reliability.
- **Database Schema Management**: Includes migration scripts for initializing and modifying the database schema, supporting version control and smooth updates.

## Key Features

### 1. Main Application
- **`med.voll.api.ApiApplication`**: The Spring Boot entry point that initializes the environment and runs the application.  
  Reference: [ApiApplication.java](file:///mnt/data/ApiApplication.java)

### 2. CORS Configuration
- **`med.voll.api.config.CorsConfiguration`**: Configures CORS to allow cross-origin requests, enabling interaction with external frontends or services.  
  Reference: [CorsConfiguration.java](file:///mnt/data/CorsConfiguration.java)

### 3. Global Exception Handler
- **`med.voll.api.controller.GlobalExceptionHandler`**: Manages global exception handling for the application, providing meaningful error responses and statuses for database, validation, and other runtime issues.  
  Reference: [GlobalExceptionHandler.java](file:///mnt/data/GlobalExceptionHandler.java)

### 4. Doctor Controller
- **`med.voll.api.controller.MedicoController`**: Handles API endpoints for managing doctors, including listing, registration, updates, and logical deletions.  
  Reference: [MedicoController.java](file:///mnt/data/MedicoController.java)

### 5. Address Data
- **`med.voll.api.direccion.DatosDireccion`**: Represents structured data for addresses with validation constraints to ensure data integrity.  
  Reference: [DatosDireccion.java](file:///mnt/data/DatosDireccion.java)

### 6. Address Model
- **`med.voll.api.direccion.Direccion`**: Represents an embeddable entity for addresses, with support for creating and updating address details dynamically.  
  Reference: [Direccion.java](file:///mnt/data/Direccion.java)

### 7. Doctor Update Data
- **`med.voll.api.medico.DatosActualizaMedico`**: Provides a structured representation of the data required to update doctor information, including validations.  
  Reference: [DatosActualizaMedico.java](file:///mnt/data/DatosActualizaMedico.java)

### 8. Doctor List Data
- **`med.voll.api.medico.DatosListadoMedico`**: Represents structured data for listing doctors, mapping essential information from the `Medico` entity.  
  Reference: [DatosListadoMedico.java](file:///mnt/data/DatosListadoMedico.java)

### 9. Doctor Registration Data
- **`med.voll.api.medico.DatosRegistroMedico`**: Represents the structured data required to register a doctor, including validations for fields like email, phone, and specialty.  
  Reference: [DatosRegistroMedico.java](file:///mnt/data/DatosRegistroMedico.java)

### 10. Specialty Enum
- **`med.voll.api.medico.Especialidad`**: An enumeration for doctor specialties, supporting both JSON serialization and deserialization with validation for valid values.  
  Reference: [Especialidad.java](file:///mnt/data/Especialidad.java)

### 11. Specialty Converter
- **`med.voll.api.medico.EspecialidadConverter`**: A JPA converter to handle the serialization and deserialization of the `Especialidad` enum to and from the database.  
  Reference: [EspecialidadConverter.java](file:///mnt/data/EspecialidadConverter.java)

### 12. Doctor Model
- **`med.voll.api.medico.Medico`**: Represents the `Medico` entity in the database, encapsulating attributes like name, phone, email, specialty, and address. Includes methods for updating data and logical deletion.  
  Reference: [Medico.java](file:///mnt/data/Medico.java)

### 13. Doctor Repository
- **`med.voll.api.medico.MedicoRepository`**: Provides advanced database queries for managing `Medico` entities, such as checking for duplicate records and filtering active doctors.  
  Reference: [MedicoRepository.java](file:///mnt/data/MedicoRepository.java)

### 14. Database Migration Script: Create Table
- **`src/main/resources/db/migration/V1__crear_tabla_medicos.sql`**: SQL script for creating the `medicos` table with all necessary fields.  
  Reference: [V1__crear_tabla_medicos.sql](file:///mnt/data/V1__crear_tabla_medicos.sql.sql)

### 15. Database Migration Script: Update Phone Column
- **`src/main/resources/db/migration/V2__update_columna_telefono.sql`**: SQL script to modify the phone column in the `medicos` table.  
  Reference: [V2__update_columna_telefono.sql](file:///mnt/data/V2__update_columna_telefono.sql)

### 16. Database Migration Script: Add Active Column
- **`src/main/resources/db/migration/V3__update_columna_activo.sql`**: SQL script to add an `activo` column to the `medicos` table.  
  Reference: [V3__update_columna_activo.sql](file:///mnt/data/V3__update_columna_activo.sql)

## System Requirements
To run this application, ensure the following prerequisites are met:
- **Java SDK 17 or higher**: The application is developed using Java 17.
- **Spring Boot Framework**: Facilitates application setup and dependency management.
- **Internet Connection**: Required for accessing external services if needed.

## How to Run
1. **Clone the Repository**: Clone the project repository using the following command:
   ```bash
   git clone https://github.com/DiiegoA/desarrollar-una-API-Rest-en-Java.git
   ```

2. **Build and Run**: Use Maven or your preferred IDE to build and run the application.
   ```bash
   mvn spring-boot:run
   ```
