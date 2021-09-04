INSERT INTO prog_educativos (id,nombre,descripcion,clave,vigencia,abreviatura,tipo,src) VALUES
(nextval('prog_educativos_sequence'),'Maestría en Computación Aplicada','Adquiere conocimientos avanzados en tecnologías emergentes: Ingeniería de Software, Sistemas Distribuidos, Bases de Datos, inteligencia artificial y TIC’s en la educación.','MCA-2941','2029-07-01','MCA','Maestria','http://www.lania.mx/wp-content/uploads/2020/05/logoMCA.png'),
(nextval('prog_educativos_sequence'),'Maestría en Redes y Sistemas Integrados Presencial','Adquiere una visión integral y sólida en el uso de la tecnología para: la construcción y diseño de sistemas computacionales, el manejo de datos y la administración de redes.','MRYSI-020MEP','2028-05-01','MRYSI-P','Maestria','http://www.lania.mx/wp-content/uploads/2020/05/logoMR.png'),
(nextval('prog_educativos_sequence'),'Diplomado en Administración de Redes','En este diplomado aprenderás todo el proceso de Ruteo configuracion y manejo de Redes Lan y WAN','DPA-12001','2028-05-01','DPA','Diplomado','http://www.lania.mx/wp-content/uploads/2020/05/thumbnail-1.png'),
(nextval('prog_educativos_sequence'),'Diplomado en Ingeniería de Software','En este diplomado aprenderás todo el proceso, metodologías y herramientas que conlleva todo el proceso de la ingenieria de software','DPA-11001','2028-06-01','DPA','Diplomado','http://www.lania.mx/wp-content/uploads/2020/05/thumbnail-1.png'),
(nextval('prog_educativos_sequence'),'Maestría en Redes y Sistemas Integrados en Línea','Adquiere una visión integral y sólida en el uso de la tecnología para: la construcción y diseño de sistemas computacionales, el manejo de datos y la administración de redes desde la modalidad en Línea a través de las plataformas que te brinda LANIA','MRYSI-L9310','2029-01-01','MRYSI-L','Maestria','http://www.lania.mx/wp-content/uploads/2020/05/logoMR.png');

INSERT INTO convocatorias(id, cant_aspirantes, created_at, descripcion, nombre, fecha_examen, fecha_inicio,fecha_termino, hora_examen, programa_educativo_id) VALUES
( nextval('convocatorias_sequence'), 20, current_timestamp , 'Convocatoria de la Maestría en Computación Aplicada 2021','Convocatoria MCA 2021','2021-07-15','2021-07-08','2021-07-10','18:00:00',1),
( nextval('convocatorias_sequence'), 23, current_timestamp , 'Convocatoria de la Maestría en Computación en Redes y Sistemas Integrados Presencial','Convocatoria MRYSI 2021 - presencial','2021-07-18','2021-07-10','2021-07-01','18:00:00',2),
( nextval('convocatorias_sequence'), 23, current_timestamp , 'Convocatoria del Diplomado en Administración de Redes 2021','Convocatoria DIP-Redes 2021 - presencial','2021-07-18','2021-07-10','2021-07-01','18:00:00',3);

INSERT INTO requisitos (id, descripcion, nombre, es_cambiante, es_documento, tipo, created_at) VALUES
(nextval('requisitos_sequence'), 'Titulo de Licenciatura', 'Titulo Licenciatura', false,true,'Documento probatorio',current_timestamp ),
(nextval('requisitos_sequence'), 'Certificado de Estudios de Licenciatura', 'Certificado de Licenciatura', false,true,'Documento probatorio',current_timestamp ),
(nextval('requisitos_sequence'), 'Acta de nacimiento', 'Acta de nacimiento original', false,true,'Documento de identidad',current_timestamp ),
(nextval('requisitos_sequence'), 'Clave Unica de Registro de Poblacion', 'CURP', false,true,'Documento de identidad',current_timestamp );

INSERT INTO requisitos_convocatorias (id,convocatoria_id,requisito_id,cantidad,indispensable,original ) VALUES
(nextval('req_convocatorias_sequence'), 1,1,2,true,'ambos'),
(nextval('req_convocatorias_sequence'), 1,2,2,true,'ambos'),
(nextval('req_convocatorias_sequence'), 1,3,1,true,'original'),
(nextval('req_convocatorias_sequence'), 2,1,1,true,'ambos'),
(nextval('req_convocatorias_sequence'), 2,2,1,true,'ambos'),
(nextval('req_convocatorias_sequence'), 2,3,1,true,'ambos'),
(nextval('req_convocatorias_sequence'), 3,1,1,true,'copia'),
(nextval('req_convocatorias_sequence'), 3,2,1,true,'copia');

INSERT INTO usuario (id,email,enabled,locked,password,usuario_rol) VALUES
(nextval('usuario_sequence'),'admin@lania.edu.mx',true,false,'$2a$12$NE5kYvRIRun1FENK5RnQc.ltHFFEkhGDncqs825ffsTpXYaPtRI1u','ADMIN');

