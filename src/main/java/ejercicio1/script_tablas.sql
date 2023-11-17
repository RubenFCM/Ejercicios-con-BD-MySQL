DROP TABLE IF EXISTS empleados_proyectos;
DROP TABLE IF EXISTS coches;
DROP TABLE IF EXISTS empleados;
DROP TABLE IF EXISTS proyectos;
DROP TABLE IF EXISTS empresas;

CREATE TABLE `empresas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `capital` double,
  `cif` varchar(255) NOT NULL,
  `razon_social` varchar(255),
  `año` int(11) ,
  PRIMARY KEY (`id`)
);

CREATE TABLE `proyectos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comienzo` date,
  `titulo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `empleados` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_nacimiento` date ,
  `category` varchar(255) ,
  `email` varchar(255) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `apellidos` varchar(255) ,
  `fecha_contratacion` date ,
  `salario` double ,
  `empresa_id` bigint(20) ,
  PRIMARY KEY (`id`)
);


CREATE TABLE `coches` (
  `id` bigint(20)  AUTO_INCREMENT,
  `cc` double,
  `fabricante` varchar(255),
  `modelo` varchar(255) NOT NULL,
  `año_lanzamiento` INT,
  `empleado_id` bigint(20),
  PRIMARY KEY (`id`)
);


CREATE TABLE empleados_proyectos(
  `id` bigint(20) AUTO_INCREMENT,
  `empleado_id` bigint(20)  NOT NULL,
  `proyecto_id` bigint(20)  NOT NULL,
  PRIMARY KEY (`id`)
);


