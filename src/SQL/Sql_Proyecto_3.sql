create
database dbAlquilerInstrumentos;

use
dbAlquilerInstrumentos;

create table Clientes
(
    dni              varchar(15) primary key,
    nombre           varchar(50),
    apellidos        varchar(50),
    fecha_nacimiento date,
    email            varchar(50),
    telefono         varchar(20),
    deuda_pendiente double default 0
);

create table Instrumentos
(
    id               int primary key auto_increment,
    marca            varchar(50),
    modelo           varchar(50),
    categoria        enum('GUITARRA', 'TECLADO', 'BATERIA', 'VIOLIN', 'BAJO', 'OTRO'),
    precio_dia double,
    stock_total      int,
    stock_disponible int,
    estado           enum ('DISPONIBLE', 'SIN_STOCK', 'MANTENIMIENTO') default 'DISPONIBLE'
);

create table Alquileres
(
    id                 int primary key auto_increment,
    dni_cliente        varchar(15),
    id_instrumento     int,
    fecha_inicio       date,
    fecha_fin_prevista date,
    fecha_devolucion   date null,
    importe_base double,
    penalizacion double default 0,
    observaciones      varchar(255),
    estadopago         boolean default false,
    foreign key (dni_cliente) references Clientes (dni),
    foreign key (id_instrumento) references Instrumentos (id)
);

create table Reservas
(
    id                    int primary key auto_increment,
    dni_cliente           varchar(15),
    id_instrumento        int,
    fecha_reserva         date,
    posicion_lista_espera int,
    activa                boolean default true,
    foreign key (dni_cliente) references Clientes (dni),
    foreign key (id_instrumento) references Instrumentos (id)
);

create table Penalizaciones
(
    id          int primary key auto_increment,
    id_alquiler int,
    motivo      varchar(100),
    importe double,
    desperfecto ENUM('NINGUNO','LEVE','MODERADO','GRAVE'),
    foreign key (id_alquiler) references Alquileres (id)
);

-- =========================
-- CLIENTES
-- =========================
INSERT INTO Clientes (dni, nombre, apellidos, fecha_nacimiento, email, telefono, deuda_pendiente)
VALUES ('12345678A', 'Mario', 'Perez Lopez', '2000-05-15', 'mario.perez@email.com', '600111222', 0),
       ('23456789B', 'Lucia', 'Martinez Garcia', '1998-11-03', 'lucia.martinez@email.com', '600222333', 15.50),
       ('34567890C', 'David', 'Sanchez Ruiz', '2003-02-21', 'david.sanchez@email.com', '600333444', 0),
       ('45678901D', 'Paula', 'Navarro Torres', '1995-08-09', 'paula.navarro@email.com', '600444555', 8.00),
       ('56789012E', 'Sergio', 'Romero Diaz', '2001-12-01', 'sergio.romero@email.com', '600555666', 0),
       ('67890123F', 'Elena', 'Castro Molina', '1999-06-17', 'elena.castro@email.com', '600666777', 20.00);

-- =========================
-- INSTRUMENTOS
-- =========================
INSERT INTO Instrumentos (marca, modelo, categoria, precio_dia, stock_total, stock_disponible, estado)
VALUES ('Yamaha', 'F310', 'GUITARRA', 12.50, 5, 3, 'DISPONIBLE'),
       ('Casio', 'CT-S300', 'TECLADO', 18.00, 3, 1, 'DISPONIBLE'),
       ('Pearl', 'Roadshow', 'BATERIA', 30.00, 2, 0, 'SIN_STOCK'),
       ('Stentor', 'Student I', 'VIOLIN', 14.00, 4, 2, 'DISPONIBLE'),
       ('Ibanez', 'GSR200', 'BAJO', 16.50, 2, 0, 'SIN_STOCK'),
       ('Roland', 'FP-10', 'TECLADO', 22.00, 1, 0, 'MANTENIMIENTO'),
       ('Fender', 'CD-60', 'GUITARRA', 15.00, 2, 2, 'DISPONIBLE'),
       ('Mapex', 'Storm', 'BATERIA', 28.00, 1, 1, 'DISPONIBLE');

-- =========================
-- ALQUILERES
-- =========================
-- Instrumentos usados según los IDs generados arriba:
-- 1 Yamaha F310
-- 2 Casio CT-S300
-- 3 Pearl Roadshow
-- 4 Stentor Student I
-- 5 Ibanez GSR200
-- 6 Roland FP-10
-- 7 Fender CD-60
-- 8 Mapex Storm

INSERT INTO Alquileres (dni_cliente, id_instrumento, fecha_inicio, fecha_fin_prevista, fecha_devolucion, importe_base,
                        penalizacion, observaciones, estadopago)
VALUES
-- alquiler activo, aún no devuelto
('12345678A', 1, '2026-04-20', '2026-04-25', NULL, 62.50, 0, 'Alquiler activo de guitarra Yamaha', true),

-- devuelto a tiempo
('34567890C', 4, '2026-04-10', '2026-04-15', '2026-04-15', 70.00, 0, 'Devuelto en buen estado', true),

-- devuelto tarde, con penalización
('23456789B', 2, '2026-04-01', '2026-04-05', '2026-04-07', 72.00, 15.50, 'Devolucion con retraso de 2 dias', false),

-- alquiler activo que deja el bajo sin stock
('45678901D', 5, '2026-04-18', '2026-04-24', NULL, 99.00, 0, 'Bajo alquilado para ensayo', true),

-- devuelto con desperfecto leve
('67890123F', 3, '2026-04-08', '2026-04-12', '2026-04-12', 120.00, 20.00, 'Se detecta pequeno desperfecto al devolver',
 false),

-- alquiler activo de batería disponible
('56789012E', 8, '2026-04-21', '2026-04-23', NULL, 84.00, 0, 'Alquiler corto para evento', true);

-- =========================
-- RESERVAS
-- =========================
INSERT INTO Reservas (dni_cliente, id_instrumento, fecha_reserva, posicion_lista_espera, activa)
VALUES
-- reservas para instrumento sin stock
('56789012E', 5, '2026-04-22', 1, true),
('12345678A', 5, '2026-04-22', 2, true),

-- reserva para instrumento en mantenimiento
('34567890C', 6, '2026-04-21', 1, true),

-- reserva para batería sin stock
('45678901D', 3, '2026-04-20', 1, true),

-- reserva ya no activa
('67890123F', 2, '2026-04-12', 1, false);

-- =========================
-- PENALIZACIONES
-- =========================
-- Relacionadas con los alquileres insertados arriba:
-- id 1 -> Mario
-- id 2 -> David
-- id 3 -> Lucia
-- id 4 -> Paula
-- id 5 -> Elena
-- id 6 -> Sergio

INSERT INTO Penalizaciones (id_alquiler, motivo, importe, desperfecto)
VALUES (3, 'Retraso en la devolucion de 2 dias', 15.50, 'NINGUNO'),
       (5, 'Golpe leve en el casco de la bateria', 20.00, 'LEVE');

SELECT *
FROM Clientes;
SELECT *
FROM Instrumentos;
SELECT *
FROM Alquileres;
SELECT *
FROM Reservas;
SELECT *
FROM Penalizaciones;

ALTER TABLE Alquileres
    CHANGE COLUMN fecha_devolucion fecha_fin_real DATE NULL;

SELECT a.id,
       c.nombre,
       c.apellidos,
       i.marca,
       i.modelo,
       a.fecha_inicio,
       a.fecha_fin_prevista,
       a.fecha_fin_real,
       a.importe_base,
       a.penalizacion,
       a.estadopago
FROM Alquileres a
         JOIN Clientes c ON a.dni_cliente = c.dni
         JOIN Instrumentos i ON a.id_instrumento = i.id;
