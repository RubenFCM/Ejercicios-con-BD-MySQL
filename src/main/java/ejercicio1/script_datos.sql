-- Tabla empresas
ALTER TABLE empresas AUTO_INCREMENT = 1;
INSERT INTO empresas (capital, cif, razon_social, año) VALUES (1000000.00, '12345678A', 'Innovatech Solutions', 2000);
INSERT INTO empresas (capital, cif, razon_social, año) VALUES (2000000.00, '98765432B', 'CodeCrafters', 2010);
INSERT INTO empresas (capital, cif, razon_social, año) VALUES (1500000.00, '56789012C', 'Nexus Innovations', 2005);
INSERT INTO empresas (capital, cif, razon_social, año) VALUES (800000.00, '34567890D', 'Synergetic Systems', 2015);
-- Tabla proyectos
ALTER TABLE proyectos AUTO_INCREMENT = 1;
INSERT INTO proyectos (comienzo, titulo) VALUES ('2023-01-01', 'CodeFusion');
INSERT INTO proyectos (comienzo, titulo) VALUES ('2022-05-15', 'FusionWorks');
INSERT INTO proyectos (comienzo, titulo) VALUES ('2023-03-10', 'CyberPulse');
INSERT INTO proyectos (comienzo, titulo) VALUES ('2022-11-20', 'QuantumQuest');
-- Tabla empleados
ALTER TABLE empleados AUTO_INCREMENT = 1;
INSERT INTO empleados (fecha_nacimiento, category, email, nombre, apellidos, fecha_contratacion, salario, empresa_id) VALUES ('1990-03-15', 'Analista', 'analista1@example.com', 'Juan', 'Pérez', '2021-01-15', 3500.00, 1);
INSERT INTO empleados (fecha_nacimiento, category, email, nombre, apellidos, fecha_contratacion, salario, empresa_id) VALUES ('1988-07-10', 'Desarrollador', 'desarrollador1@example.com', 'María', 'Gómez', '2019-05-10', 2800.00, 2);
INSERT INTO empleados (fecha_nacimiento, category, email, nombre, apellidos, fecha_contratacion, salario, empresa_id) VALUES ('1995-11-20', 'Gerente', 'gerente1@example.com', 'Carlos', 'Martínez', '2022-02-28', 4500.00, 1);
INSERT INTO empleados (fecha_nacimiento, category, email, nombre, apellidos, fecha_contratacion, salario, empresa_id) VALUES ('1985-02-05', 'Analista', 'analista2@example.com', 'Laura', 'Sánchez', '2018-09-03', 3800.00, 3);
-- Tabla coches
ALTER TABLE coches AUTO_INCREMENT = 1;
INSERT INTO coches (cc, fabricante, modelo, año_lanzamiento, empleado_id) VALUES (1500.00, 'Toyota', 'Corolla', 2022, 1);
INSERT INTO coches (cc, fabricante, modelo, año_lanzamiento, empleado_id) VALUES (1800.00, 'Ford', 'Mustang', 2021, 2);
INSERT INTO coches (cc, fabricante, modelo, año_lanzamiento, empleado_id) VALUES (2000.00, 'Honda', 'Civic', 2023, 3);
INSERT INTO coches (cc, fabricante, modelo, año_lanzamiento, empleado_id) VALUES (1600.00, 'Chevrolet', 'Camaro', 2020, 4);
-- Tabla empleados_proyectos
ALTER TABLE empleados_proyectos AUTO_INCREMENT = 1;
INSERT INTO empleados_proyectos (empleado_id, proyecto_id) VALUES (1, 1);
INSERT INTO empleados_proyectos (empleado_id, proyecto_id) VALUES (2, 1);
INSERT INTO empleados_proyectos (empleado_id, proyecto_id) VALUES (3, 2);
INSERT INTO empleados_proyectos (empleado_id, proyecto_id) VALUES (4, 3);