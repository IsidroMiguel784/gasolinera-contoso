# Sistema de Gestión de Gasolinera - CONTOSO S.L.

Este proyecto implementa un sistema de gestión para la gasolinera de CONTOSO S.L., permitiendo el control de surtidores, productos, tanques y registro de suministros de combustible.

## Características

- **Gestión de Surtidores**: Alta, baja y mantenimiento de surtidores con sus productos asociados.
- **Control de Productos**: Gestión de los 4 tipos de combustible (gasolina 95, 98, diésel normal y premium).
- **Precios**: Establecimiento de precios mensuales para cada producto.
- **Tanques**: Monitoreo de 4 tanques subterráneos con capacidad de 10.000 litros cada uno.
- **Suministros**: Registro detallado de cada operación de repostaje (fecha, combustible, importe, volumen).

## Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (desarrollo)
- MySQL (producción)
- Lombok
- RESTful API

## Requisitos

- JDK 17 o superior
- Maven 3.6 o superior
- MySQL (opcional, para producción)

## Instalación y Ejecución

### Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/gasolinera-contoso.git
cd gasolinera-contoso
