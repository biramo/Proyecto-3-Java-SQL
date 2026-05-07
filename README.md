# 🎸 Sistema de Gestión de Alquiler de Instrumentos Musicales

Aplicación de consola desarrollada en Java para la gestión de alquiler de instrumentos musicales utilizando Programación
Orientada a Objetos, JDBC y MySQL.

El sistema permite gestionar clientes, instrumentos, alquileres, devoluciones, reservas y penalizaciones desde menús
organizados por consola.

---

# 📚 Tecnologías utilizadas

- Java
- JDBC
- MySQL
- Programación Orientada a Objetos (POO)
- Arquitectura por capas
- Colecciones (`ArrayList`, `Iterator`)
- Enumeraciones (`enum`)
- Interfaces y clases abstractas

---

# 🎯 Objetivo del proyecto

La aplicación tiene como objetivo simular el funcionamiento interno de una empresa de alquiler de instrumentos musicales
permitiendo:

- Gestión completa de clientes
- Gestión de instrumentos
- Registro de alquileres
- Registro de devoluciones
- Gestión de reservas y lista de espera
- Gestión de penalizaciones
- Persistencia de datos en MySQL

---

# 🏗️ Arquitectura del proyecto

El proyecto se organiza en distintos paquetes para separar responsabilidades y facilitar el mantenimiento.

```text
src/
│
├── App/
│   └── Main.java
│
├── Controller/
│   ├── ClienteCRUD.java
│   ├── InstrumentoCRUD.java
│   ├── AlquilerCRUD.java
│   ├── ReservaCRUD.java
│   ├── PenalizacionCRUD.java
│   └── ConexionBD.java
│
├── Services/
│   ├── ServiceMenu.java
│   ├── ServiceClientes.java
│   ├── ServiceInstrumento.java
│   ├── ServiceAlquiler.java
│   ├── ServiceReservas.java
│   └── ServicePenalizaciones.java
│
├── Menu/
│   ├── MenuPrincipal.java
│   ├── MenuClientes.java
│   ├── MenuInstrumentos.java
│   ├── MenuAlquileres.java
│   ├── MenuReservas.java
│   └── MenuPenalizaciones.java
│
├── model/
│   ├── Persona.java
│   ├── Cliente.java
│   ├── Instrumento.java
│   ├── Alquiler.java
│   ├── Reserva.java
│   ├── Penalizacion.java
│   ├── Empresa.java
│   ├── AccesoAdministrador.java
│   │
│   ├── Enum/
│   └── Interfaces/
│
└── Funciones/
    ├── Validacion.java
    └── ControlErrores.java
```

---

# ⚙️ Funcionalidades principales

## 👤 Gestión de clientes

- Alta de clientes
- Modificación
- Eliminación
- Listado completo
- Búsqueda por DNI
- Búsqueda por email

---

## 🎸 Gestión de instrumentos

- Alta de instrumentos
- Modificación
- Eliminación
- Listado completo
- Búsqueda por ID
- Búsqueda por categoría
- Búsqueda por marca
- Búsqueda por estado
- Gestión del stock

---

## 📦 Gestión de alquileres

- Registrar alquileres
- Consultar alquileres activos
- Buscar alquileres por:
    - ID
    - Cliente
    - Instrumento
- Registrar devoluciones
- Marcar alquileres como pagados

---

## ⏳ Gestión de reservas

- Crear reservas
- Gestionar lista de espera
- Cancelar reservas
- Confirmar reservas cuando exista stock

---

## ⚠️ Gestión de penalizaciones

- Penalizaciones por retraso
- Penalizaciones por desperfectos
- Consulta de penalizaciones
- Gestión del estado de pago

---

# 🧠 Características POO aplicadas

## ✅ Herencia

```text
Persona → Cliente
```

---

## ✅ Interfaces

Interfaces utilizadas:

- `InterCliente`
- `Alquilable`

### 🔹 Interfaz `InterCliente`

Define comportamientos comunes relacionados con los clientes del sistema.

```java
public interface InterCliente {

    int calcularEdad();

    double totalAlquileres();

}
```

---

### 🔹 Interfaz `Alquilable`

Define el comportamiento común de cualquier elemento alquilable dentro de la aplicación.

Actualmente implementada por:

- `Instrumento`

Preparada para futuras ampliaciones como:

- `EstudioGrabacion`

```java
public interface Alquilable {

    boolean estaDisponible();

    double calcularImporte(int dias);

    void registrarSalida();

    void registrarEntrada();

}
```

---

## ✅ Clase abstracta

```text
Persona
```

---

## ✅ Polimorfismo

Aplicado en métodos como:

- `mostrarInformacion()`
- `calcularImporte()`
- `mostrarResumen()`

---

## ✅ Enumeraciones

Enums utilizados:

- `CategoriaInstrumento`
- `EstadoInstrumento`
- `EstadoPago`
- `TipoDesperfecto`

---

### 🎸 Enum `CategoriaInstrumento`

Clasifica los instrumentos musicales.

```java
public enum CategoriaInstrumento {

    GUITARRA,
    TECLADO,
    BATERIA,
    VIOLIN,
    BAJO,
    OTRO

}
```

---

### 📦 Enum `EstadoInstrumento`

Representa el estado actual del instrumento.

```java
public enum EstadoInstrumento {

    DISPONIBLE,
    SIN_STOCK,
    MANTENIMIENTO

}
```

---

### 💳 Enum `EstadoPago`

Representa el estado del pago de alquileres o penalizaciones.

```java
public enum EstadoPago {

    PENDIENTE,
    PAGADO

}
```

---

### ⚠️ Enum `TipoDesperfecto`

Clasifica los daños detectados durante una devolución.

```java
public enum TipoDesperfecto {

    NINGUNO,
    LEVE,
    MODERADO,
    GRAVE

}
```

---

## ✅ Colecciones

Uso de:

```java
ArrayList<Cliente>
ArrayList<Instrumento>
ArrayList<Alquiler>
ArrayList<Reserva>
```

---

## ✅ Iterator

Los listados se recorren mediante `Iterator`.

---

## ✅ Comparable

Implementado para ordenar:

- Clientes
- Instrumentos

---

## ✅ Cloneable

Implementado en:

- Cliente
- Instrumento

---

## ✅ equals()

Sobrescrito para comparar:

- Clientes por DNI
- Instrumentos por ID

---

# 🗄️ Base de datos

La aplicación utiliza MySQL mediante JDBC.

## Tablas principales

- clientes
- instrumentos
- alquileres
- reservas
- penalizaciones

---

# 📌 Ejemplo tabla instrumentos

```sql
CREATE TABLE instrumentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    categoria ENUM(
        'GUITARRA',
        'TECLADO',
        'BATERIA',
        'VIOLIN',
        'BAJO',
        'OTRO'
    ),
    precio_dia DOUBLE,
    stock_total INT,
    stock_disponible INT,
    estado ENUM(
        'DISPONIBLE',
        'SIN_STOCK',
        'MANTENIMIENTO'
    ) DEFAULT 'DISPONIBLE'
);
```

---

# 🔄 Gestión del stock

Cada instrumento dispone de:

- `stock_total`
- `stock_disponible`

## Funcionamiento

- Al realizar un alquiler:
    - el stock disminuye.
- Al registrar una devolución:
    - el stock aumenta.
- Si el instrumento presenta daños graves:
    - puede pasar a `MANTENIMIENTO`.

---

# ⌛ Sistema de reservas y lista de espera

Cuando un instrumento no tiene stock disponible:

1. El cliente puede crear una reserva.
2. Se guarda una posición en lista de espera.
3. Cuando vuelva a haber stock:
    - la reserva puede confirmarse.

---

# ⚠️ Sistema de penalizaciones

Las penalizaciones se generan por:

- retrasos en devoluciones
- desperfectos

## Tipos de desperfecto

- NINGUNO
- LEVE
- MODERADO
- GRAVE

---

# 🔐 Acceso administrador

Antes de acceder al sistema:

- el usuario debe introducir un código de administrador.
- si el código es correcto:
    - se accede al menú principal.

---

# 🚀 Ejecución del proyecto

## Requisitos

- Java 17+
- MySQL
- IDE Java (IntelliJ IDEA recomendado)

---

## Pasos para ejecutar

### 1. Clonar repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

---

### 2. Configurar base de datos MySQL

Crear la base de datos e importar las tablas.

---

### 3. Configurar conexión JDBC

Modificar los datos de conexión en:

```text
ConexionBD.java
```

---

### 4. Ejecutar

Ejecutar:

```text
Main.java
```

---

# 📖 Documentación

El proyecto incluye documentación Javadoc en:

```text
ServiceMenu
```

---

# 🔮 Posible ampliación futura

El proyecto queda preparado para una futura ampliación:

## 🎙️ Alquiler de estudios de grabación

Gracias a la interfaz:

```text
Alquilable
```

la aplicación podrá gestionar:

- instrumentos musicales
- estudios de grabación

sin modificar la arquitectura principal.

---

# 🤖 Uso de Inteligencia Artificial

Este proyecto fue desarrollado con apoyo de herramientas de inteligencia artificial para:

- revisión de código
- resolución de dudas técnicas
- optimización de estructuras
- explicaciones de conceptos
- mejora de clean code y arquitectura

Todo el código fue revisado, comprendido y adaptado manualmente.

---

# 👨‍💻 Autor

Proyecto desarrollado por:

**[TU_NOMBRE]**

---

# 📌 Estado del proyecto

✅ En desarrollo / Finalizado
