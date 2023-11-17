-- INSERTs para CATEGORIAS
INSERT INTO CATEGORIAS VALUES (1, 'Comida');
INSERT INTO CATEGORIAS VALUES (2, 'Bebida');
INSERT INTO CATEGORIAS VALUES (3, 'Otros');
-- INSERTs para PROVEEDORES
ALTER TABLE PROVEEDORES AUTO_INCREMENT = 1;
INSERT INTO PROVEEDORES (nombre, contacto, telefono, email) VALUES ('Innovate Supply Co.', 'Ana Martínez', '123-456-7890', 'ana@innovatesupply.com');
INSERT INTO PROVEEDORES (nombre, contacto, telefono, email) VALUES ('EcoSolutions Ltd.', 'Carlos Rodríguez', '987-654-3210', 'carlos@ecosolutions.com');
INSERT INTO PROVEEDORES (nombre, contacto, telefono, email) VALUES ('StellarCraft Providers', 'Laura García', '555-123-4567', 'laura@stellarcraft.com');
INSERT INTO PROVEEDORES (nombre, contacto, telefono, email) VALUES ('NexusTech Enterprises', 'David López', '777-888-9999', 'david@nexustech.com');
-- INSERTs para CLIENTES
ALTER TABLE CLIENTES AUTO_INCREMENT = 1;
INSERT INTO CLIENTES VALUES (7000, 'Paco Menendez', 'Plza. Rodrigo Botet, 5');
INSERT INTO CLIENTES VALUES (7001, 'Pepe Luis Jimenez', 'Av. del Puerto, 214');
INSERT INTO CLIENTES VALUES (7004, 'Francisco López Serrano', 'C/Cuenca, 68');
-- INSERTs para PRODUCTOS
INSERT INTO PRODUCTOS VALUES (8000,'Plato de macarrones',10, 1,2);
INSERT INTO PRODUCTOS VALUES (8001,'Botella de agua',2, 2,1);
INSERT INTO PRODUCTOS VALUES (8002,'Botella de vino',20, 2,3);
INSERT INTO PRODUCTOS VALUES (8003,'Barra de pan',1, 1,2);
-- INSERTs para VENTAS
INSERT INTO VENTAS VALUES (1, 8000, 7000, '2021-10-11', 5);
INSERT INTO VENTAS VALUES (2, 8001, 7004, '2020-12-17', 7);
INSERT INTO VENTAS VALUES (3, 8002, 7000, '2023-08-02', 4);
INSERT INTO VENTAS VALUES (4, 8002, 7001, '2021-09-09', 3);
INSERT INTO VENTAS VALUES (5, 8001, 7001, '2019-10-14', 10);