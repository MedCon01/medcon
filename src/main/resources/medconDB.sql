-- H2 2.1.214;
;             
CREATE USER IF NOT EXISTS "SA" SALT '9272b2bc8938c9c4' HASH '86bd81143aced5d60e880aba7a09b7db8c91c1a931e9cca0779f592b05ea48f4' ADMIN;         
CREATE CACHED TABLE "PUBLIC"."PACIENTE"(
    "ID" CHARACTER VARYING(6) NOT NULL,
    "DNI" CHARACTER VARYING(9) NOT NULL,
    "NOMBRE" CHARACTER VARYING(255) NOT NULL,
    "FECHA_NACIMIENTO" DATE NOT NULL,
    "NTARJETA" BIGINT NOT NULL,
    "CITA" INTEGER,
    "PRESENTE" BOOLEAN NOT NULL
);  
ALTER TABLE "PUBLIC"."PACIENTE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_E" PRIMARY KEY("ID");     
-- 13 +/- SELECT COUNT(*) FROM PUBLIC.PACIENTE;               
INSERT INTO "PUBLIC"."PACIENTE" VALUES
('AGI493', '12345678A', U&'Antonio Garc\00eda Iglesias', DATE '1984-09-13', 123456789012, NULL, FALSE),
('RCN502', '23456789B', U&'Ra\00fal Cervantes N\00fa\00f1ez', DATE '1998-03-17', 234567890123, NULL, FALSE),
('MRM837', '34567890C', U&'Mar\00eda Rodr\00edguez Ruiz', DATE '1985-10-12', 345678901234, NULL, FALSE),
('ALB988', '45678901D', U&'Ana L\00f3pez Bilbao', DATE '1979-08-28', 456789012345, NULL, FALSE),
('FLC143', '56789012E', 'Fernando Leal Costa', DATE '1961-04-03', 567890123456, NULL, FALSE),
('RAG053', '28374950F', U&'Ramiro Arango Gonz\00e1lez', DATE '1980-05-23', 111122223333, NULL, FALSE),
('EPC217', '73629184G', 'Estela Pardo Ceballos', DATE '1992-01-17', 555566667777, NULL, FALSE),
('EAC572', '92587431H', U&'Ezequiel \00c1lvarez C\00e1rdenas', DATE '1985-07-12', 999900001111, NULL, FALSE),
('CBL624', '51893267J', U&'Candela B\00e1ez Luque', DATE '1976-12-04', 333344445555, NULL, FALSE),
('NRR880', '30918756A', 'Nuria Romero Rubio', DATE '1998-08-30', 777788889999, NULL, FALSE),
('PCI035', '64728391W', 'Pedro Castro Iglesias', DATE '1990-03-15', 444455556666, NULL, FALSE),
('SGR712', '18273649C', U&'Sonia Gonz\00e1lez Ruiz', DATE '1987-11-22', 222233334444, NULL, FALSE),
('SBC268', '49586372D', U&'Sim\00f3n Ben\00edtez Cepeda', DATE '1982-06-08', 888899990001, NULL, FALSE);            
CREATE CACHED TABLE "PUBLIC"."MEDICO"(
    "DNI" CHARACTER VARYING(9) NOT NULL,
    "NOMBRE" CHARACTER VARYING(255) NOT NULL,
    "N_COLEGIADO" BIGINT NOT NULL,
    "SALACONSULTA" INTEGER NOT NULL,
    "ESPECIALIDAD" CHARACTER VARYING(100) NOT NULL
);             
ALTER TABLE "PUBLIC"."MEDICO" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_6" PRIMARY KEY("DNI");      
-- 5 +/- SELECT COUNT(*) FROM PUBLIC.MEDICO;  
INSERT INTO "PUBLIC"."MEDICO" VALUES
('11111111A', U&'Arturo P\00e9rez Sevilla', 459234567890, 101, U&'Pediatr\00eda'),
('22222222B', U&'Marta Garc\00eda Ruiz', 588345678901, 102, U&'Cardiolog\00eda'),
('33333333C', U&'Diego Hern\00e1ndez Torres', 427456789012, 103, U&'Traumatolog\00eda'),
('44444444D', 'Carmen Ortiz Medina', 586567890123, 104, U&'Dermatolog\00eda'),
('55555555E', U&'\00c1lvaro Mart\00edn Soler', 345689012340, 105, U&'Dermatolog\00eda');               
CREATE CACHED TABLE "PUBLIC"."CITA"(
    "ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "FECHA" DATE NOT NULL,
    "HORA" TIME NOT NULL,
    "PACIENTE" CHARACTER VARYING(6) NOT NULL,
    "MEDICO" CHARACTER VARYING(9) NOT NULL
);         
ALTER TABLE "PUBLIC"."CITA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1" PRIMARY KEY("ID");         
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.CITA;    
ALTER TABLE "PUBLIC"."PACIENTE" ADD CONSTRAINT "PUBLIC"."NUMTARJETA" UNIQUE("NTARJETA");      
ALTER TABLE "PUBLIC"."MEDICO" ADD CONSTRAINT "PUBLIC"."N_COLEGIADO" UNIQUE("N_COLEGIADO");    
ALTER TABLE "PUBLIC"."PACIENTE" ADD CONSTRAINT "PUBLIC"."DNI" UNIQUE("DNI");  
ALTER TABLE "PUBLIC"."PACIENTE" ADD CONSTRAINT "PUBLIC"."CITA" FOREIGN KEY("CITA") REFERENCES "PUBLIC"."CITA"("ID") NOCHECK;  
ALTER TABLE "PUBLIC"."CITA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1F9" FOREIGN KEY("MEDICO") REFERENCES "PUBLIC"."MEDICO"("DNI") NOCHECK;       
ALTER TABLE "PUBLIC"."CITA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_1F" FOREIGN KEY("PACIENTE") REFERENCES "PUBLIC"."PACIENTE"("ID") NOCHECK;     
