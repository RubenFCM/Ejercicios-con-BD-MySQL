-- INSERTs para SOCIOS
INSERT INTO SOCIOS VALUES (7000, 'Juan', '2010-10-05');
INSERT INTO SOCIOS VALUES (7001, 'Antonio', '2014-11-15');
INSERT INTO SOCIOS VALUES (7002, 'Jose', '2016-01-09');
-- INSERTs para EVENTOS
INSERT INTO EVENTOS VALUES (8888, 'Fiesta de la espuma', '2021-12-20');
INSERT INTO EVENTOS VALUES (8889, 'Cata de vinos', '2020-11-30');
INSERT INTO EVENTOS VALUES (8890, 'Maraton de cine', '2022-05-30');
INSERT INTO EVENTOS VALUES (8891, 'Partido de futbol sala', '2023-06-30');
-- INSERTs para INSCRIPCIONES
INSERT INTO INSCRIPCIONES VALUES (1, 7000, 8888);
INSERT INTO INSCRIPCIONES VALUES (2, 7000, 8889);
INSERT INTO INSCRIPCIONES VALUES (3, 7001, 8888);
INSERT INTO INSCRIPCIONES VALUES (4, 7001, 8891);
INSERT INTO INSCRIPCIONES VALUES (5, 7002, 8889);
-- INSERTs para RESENAS_EVENTOS
ALTER TABLE RESENAS_EVENTOS AUTO_INCREMENT = 1;
INSERT INTO RESENAS_EVENTOS (evento_id, socio_id, puntuacion, comentario, fecha_creacion) VALUES (8888, 7000, 4, 'Gran evento, mucha diversión', '2020-12-21');
INSERT INTO RESENAS_EVENTOS (evento_id, socio_id, puntuacion, comentario, fecha_creacion) VALUES (8889, 7000, 5, 'Experiencia increíble, los vinos eran geniales', '2020-11-30');
INSERT INTO RESENAS_EVENTOS (evento_id, socio_id, puntuacion, comentario, fecha_creacion) VALUES (8888, 7001, 3, 'Divertido pero podría haber más actividades', '2020-12-22');
INSERT INTO RESENAS_EVENTOS (evento_id, socio_id, puntuacion, comentario, fecha_creacion) VALUES (8890, 7002, 4, 'Maratón emocionante, aunque un poco largo', '2020-06-01');
INSERT INTO RESENAS_EVENTOS (evento_id, socio_id, puntuacion, comentario, fecha_creacion) VALUES (8891, 7002, 5, 'Gran partido, mucha adrenalina', '2020-07-01');