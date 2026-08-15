# Examen Programación Visual A26

Proyecto JavaFX - Aplicación de Personajes de Los Simpsons

## Descripción

Aplicación desarrollada en JavaFX para mostrar información sobre personajes de la serie "Los Simpsons".

## Tecnologías

- **Java 11+**
- **JavaFX 21**
- **Maven**
- **FXML** para la interfaz gráfica

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/simposons/personajes/
│   │   ├── App.java                      # Aplicación principal
│   │   ├── PersonajeController.java      # Controlador de la UI
│   │   ├── model/
│   │   │   ├── Personaje.java           # Modelo de datos
│   │   │   └── SimpsonsResponse.java     # Respuesta de API
│   │   └── service/
│   │       └── SimpsonsService.java      # Servicio de API
│   └── resources/
│       └── com/simposons/personajes/
│           ├── personaje.fxml           # Interfaz gráfica
│           └── styles.css               # Estilos
```

## Requisitos

- Java JDK 11 o superior
- Maven 3.6+

## Instalación

1. Clonar el repositorio
```bash
git clone https://github.com/LILSAM2002/ExamenProgramacionVisual-SamuelESPINEL.git
cd ExamenProgramacionVisual-SamuelESPINEL
```

2. Compilar el proyecto
```bash
mvn clean install
```

3. Ejecutar la aplicación
```bash
mvn javafx:run
```

## Autor

Samuel ESPINEL
