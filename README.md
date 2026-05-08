# Proyecto P3 - Sistema de Alquiler de Instrumentos ("En las Cuerdas")

Aplicacion de consola en Java para gestionar una empresa de alquiler de instrumentos: **clientes**, **inventario**, *
*alquileres/devoluciones**, **reservas (lista de espera)** y **penalizaciones**, con persistencia en **MySQL** mediante
**JDBC**.

## 1. Requisitos y ejecucion

### Requisitos

- Java (proyecto tipo IntelliJ sin `pom.xml`/Gradle).
- MySQL en local.
- Driver JDBC de MySQL (MySQL Connector/J) configurado en el proyecto (por ejemplo como libreria en IntelliJ).

### Base de datos

La conexion esta centralizada en `Controller/ConexionBD.java`:

- URL: `jdbc:mysql://localhost:3306/dbAlquilerInstrumentos`
- Usuario: `root`
- Password: `root`

Si tu MySQL usa otras credenciales/host/puerto, ajusta esas constantes.

Script de creacion/migracion y datos de ejemplo:

- `src/SQL/Sql_Proyecto_3.sql`

Notas importantes del SQL:

- Crea la BD `dbAlquilerInstrumentos` y las tablas principales.
- Incluye varias sentencias `ALTER TABLE` que **alinean el esquema con el codigo** (por ejemplo `fecha_fin_real`,
  `importe_final`, columnas de cancelacion soft-delete, y `descripcion` en penalizaciones).
- Incluye un `SET GLOBAL time_zone = '+1:00';` (requiere permisos) pensado para evitar problemas de zona horaria en
  algunos entornos.

### Ejecucion

Entrada principal:

- `src/java/App/Main.java`

Flujo:

1. Se solicita un **codigo de administrador**.
2. Si es valido, se inicia el **menu principal**.

## 2. Flujo general del sistema

### Main y control de acceso

- `App.Main`: crea un `Scanner` y ejecuta `AccesoAdministrador.validador(sc)`. Si valida, arranca
  `ServiceMenu.initService(sc)`.
- `model.AccesoAdministrador`: valida el acceso con un codigo estatico.

### Orquestacion del menu principal

- `Services.ServiceMenu`: bucle principal que muestra `Menu.MenuPrincipal` y delega en cada subservicio.
    - Opcion **5 (Devoluciones)**: se gestiona desde el menu de **Alquileres** (se informa al usuario y se redirige).

## 3. Menus y opciones (consola)

### Menu principal (`Menu.MenuPrincipal`)

1. Menu Instrumentos
2. Menu Clientes
3. Menu Reservas
4. Menu Alquileres
5. Menu Devolucion (redirige a Alquileres)
6. Menu Penalizacion
0. Salir

### Instrumentos (`Menu.MenuInstrumentos` + `Services.ServiceInstrumento`)

1. Mostrar todos
2. Buscar por ID
3. Buscar por categoria
4. Buscar por marca
5. Buscar por estado
6. Agregar instrumento
7. Modificar instrumento
8. Eliminar instrumento
9. Cambiar estado
0. Volver

### Clientes (`Menu.MenuClientes` + `Services.ServiceClientes`)

1. Mostrar todos
2. Buscar por DNI
3. Buscar por email
4. Insertar nuevo cliente
5. Modificar cliente
6. Eliminar cliente
0. Volver

### Alquileres y devoluciones (`Menu.MenuAlquileres` + `Services.ServiceAlquiler`)

1. Mostrar todos los alquileres
2. Buscar por ID
3. Buscar por cliente (DNI)
4. Buscar por instrumento (ID)
5. Insertar nuevo alquiler
6. Modificar alquiler
7. Cancelar alquiler (soft delete)
8. Ver alquileres activos (sin devolucion registrada)
9. Registrar devolucion
10. Marcar como pagado
0. Volver

### Reservas / lista de espera (`Menu.MenuReservas` + `Services.ServiceReservas`)

1. Crear reserva
2. Ver lista de espera por instrumento
3. Cancelar reserva (por ID)
4. Confirmar reserva (crea un alquiler al primero de la cola)
0. Volver

### Penalizaciones (`Menu.MenuPenalizaciones` + `Services.ServicePenalizaciones`)

1. Listar penalizaciones
2. Buscar por ID de alquiler
3. Buscar por cliente (DNI)
0. Volver

## 4. Arquitectura por paquetes

Estructura (resumen):

- `src/java/App`: punto de entrada.
- `src/java/Menu`: UI de consola (pintado del menu, espera, etc.).
- `src/java/Services`: logica de aplicacion (orquestacion, validacion de entradas, coordinacion CRUD).
- `src/java/Controller`: capa de persistencia (CRUD JDBC y conexion).
- `src/java/model`: entidades del dominio (models), enums e interfaces.
- `src/java/Funciones`: utilidades de validacion y manejo de errores.
- `src/SQL`: script de BD.

## 5. Modelos (model) y relaciones principales

### `model.Persona` (abstracta)

Base comun con:

- `dni`, `nombre`, `apellidos`, `telefono`, `email`

### `model.Cliente` (extends `Persona`, implements `InCliente`)

Campos relevantes:

- `fechaNacimiento`

Funciones relevantes:

- `calcularEdad()`: usa `LocalDate.now()` y `Period`.
- `mostrarCliente()`: salida formateada para consola.

### `model.Instrumento` (implements `InAlquilable`)

Campos:

- `id`, `marca`, `modelo`, `precioDia`, `stockTotal`, `stockDisponible`
- `categoria` (`CategoriaInstrumento`)
- `estado` (`EstadoInstrumento`)

Funciones relevantes:

- `estaDisponible()`: `stockDisponible > 0`.
- `registrarSalida()`: decrementa stock disponible y pasa a `SIN_STOCK` si llega a 0.
- `registrarEntrada()`: incrementa stock disponible y marca `DISPONIBLE`.

### `model.Alquiler`

Representa el alquiler y su vida completa (incluye devolucion/cancelacion).

Campos:

- `cliente` (asociacion)
- `instrumento` (asociacion)
- `fechaInicio`, `fechaFinPrevista`, `fechaFinReal` (devolucion)
- `importeBase`, `importeFinal`
- `penalizaciones` (lista de `Penalizacion`)
- `observaciones`
- `estadoPago` (`EstadoPago`)
- soft delete: `cancelado`, `fechaCancelacion`, `motivoCancelacion`

Funciones relevantes:

- `calcularDiasAlquiler()`: calcula dias (minimo 1).
- `recalcularImporteFinal()`: `importeBase + suma(penalizaciones)`.
- `registrarDevolucion(LocalDate)`.

### `model.Reserva`

Reserva activa/inactiva en lista de espera por instrumento:

- `posicionListaEspera` y `activa`.
- `cancelarReserva()`: marca inactiva y delega en `ReservaCRUD.cancelarReserva(...)` para **reordenar la cola**.

### `model.Penalizacion`

Campos:

- `motivo`, `descripcion` (opcional), `importe`, `desperfecto` (`TipoDesperfecto`)

Salida:

- `mostrarPenalizacion()`.

## 6. Enums

Ubicacion: `src/java/model/Enum`

- `CategoriaInstrumento`: `GUITARRA`, `TECLADO`, `BATERIA`, `VIOLIN`, `BAJO`, `OTRO`
- `EstadoInstrumento`: `DISPONIBLE`, `SIN_STOCK`, `MANTENIMIENTO`
- `TipoDesperfecto`: `NINGUNO`, `LEVE`, `MODERADO`, `GRAVE`
- `EstadoPago`: `PENDIENTE`, `PAGADO`
    - Persistencia: se guarda/lee desde BD como **ordinal** (`0/1`) mediante `EstadoPago.desdeBD(int)`.

## 7. Interfaces

Ubicacion: `src/java/model/Interfaces`

- `InAlquilable`: contrato de disponibilidad/importe y entradas/salidas de stock.
- `InCliente`: contrato de `calcularEdad()` y `totalAlquileres()` (este ultimo esta en "standby" en el modelo).

## 8. Servicios (Services): logica de negocio

### `ServiceInstrumento`

- Centraliza busquedas por ID/categoria/marca/estado.
- Inserta/actualiza/elimina instrumentos via `InstrumentoCRUD`.
- Cambio de estado explicito con `updateEstado(...)`.

### `ServiceClientes`

- CRUD de clientes via `ClienteCRUD`.
- Entrada de datos con validaciones (`dni`, `email`, `telefono`, `fecha`).

### `ServiceAlquiler`

Operaciones clave:

- Crear/modificar alquiler: pide `dni`, `idInstrumento`, fechas y observaciones; valida que cliente e instrumento
  existan.
- **Cancelacion (soft delete)**: marca el alquiler como cancelado y guarda motivo/fecha.
- **Devolucion**:
    - Calcula retraso (`fechaFinPrevista` -> `fechaReal`) y aplica una penalizacion fija:
        - `%` configurado en `PORCENTAJE_PENALIZACION_RETRASO = 0.25` (25% del precio/dia por cada dia de retraso).
    - Si hay desperfectos, solicita tipo (`TipoDesperfecto`), descripcion e importe y registra penalizacion.
    - Puede marcar el instrumento como `MANTENIMIENTO`.
    - Persiste `fecha_fin_real` e `importe_final`.

### `ServiceReservas`

- Crea reservas activas con **posicion calculada automaticamente** por instrumento.
- Lista la cola por instrumento.
- Cancela reservas (y reordena posiciones).
- Confirma la primera reserva de la cola: crea un alquiler para ese cliente/instrumento y cancela la reserva.

### `ServicePenalizaciones`

- Inserta penalizaciones (tambien usado por devoluciones desde `ServiceAlquiler`).
- Lista penalizaciones globalmente o filtradas por alquiler/cliente.

## 9. Controllers (JDBC): conexion y CRUD

### Conexion

- `Controller.ConexionBD`: `DriverManager.getConnection(...)` con URL/credenciales.

### CRUD principales

- `ClienteCRUD`: `INSERT/UPDATE/SELECT/DELETE` de clientes.
- `InstrumentoCRUD`: `INSERT/UPDATE/SELECT/DELETE`, filtros por marca/categoria/estado y cambio de estado.
- `AlquilerCRUD`:
    - Insercion/actualizacion.
    - Listados (todos, por cliente, por instrumento, activos).
    - Registro de devolucion (actualiza `fecha_fin_real` e `importe_final`).
    - Marcar como pagado.
    - Cancelacion soft-delete (`cancelado`, `fecha_cancelacion`, `motivo_cancelacion`).
- `ReservaCRUD`:
    - Insercion con `posicion_lista_espera` calculada.
    - Cancelacion soft-delete (`activa=false`) y reordenamiento de cola.
    - Listados por instrumento/cliente.
- `PenalizacionCRUD`:
    - Insercion/listados de penalizaciones (por alquiler y globales), y busqueda por ID.

## 10. Funciones auxiliares

### Validacion de entradas (`Funciones.Validacion`)

Validadores usados en los servicios/menus:

- `validadorInt`, `validadorDouble`
- `validadorString` (solo letras y espacios; permite `"0"` para cancelar procesos)
- `validadorDni`, `validadorTelefono`, `validadorEmail`
- `validadorFechaDefault` (ISO `YYYY-MM-DD`)
- `validadorFecha(prompt, permitirVacioUsarHoy)`
- `validadorGenericoEnum(...)` (muestra opciones del enum y valida)

### Manejo de errores SQL (`Funciones.ControlErrores`)

`errorHandler(SQLException)` traduce errores comunes:

- Duplicados (`1062`), constraint FK (`1451`), credenciales, timeouts, etc.

## 11. Esquema de base de datos (resumen)

Tablas principales (ver script para detalle):

- `Clientes(dni PK, nombre, apellidos, fecha_nacimiento, email, telefono, ...)`
- `Instrumentos(id PK AI, marca, modelo, categoria ENUM, precio_dia, stock_total, stock_disponible, estado ENUM)`
-

`Alquileres(id PK AI, dni_cliente FK, id_instrumento FK, fecha_inicio, fecha_fin_prevista, fecha_fin_real NULL, importe_base, importe_final, observaciones, estadopago, cancelado, ...)`

- `Reservas(id PK AI, dni_cliente FK, id_instrumento FK, fecha_reserva, posicion_lista_espera, activa)`
- `Penalizaciones(id PK AI, id_alquiler FK, motivo, descripcion, importe, desperfecto ENUM)`

## 12. Notas rapidas

- "Devoluciones" esta integrada dentro del menu de **Alquileres** (opcion 9).
- Se prefiere **cancelar** un alquiler (soft delete) a borrarlo fisicamente.
- `MenuClientes` contiene algunas funciones que llaman directamente al `ServiceClientes` para mostrar resultados; el
  resto de menus delegan en sus `Service`.
