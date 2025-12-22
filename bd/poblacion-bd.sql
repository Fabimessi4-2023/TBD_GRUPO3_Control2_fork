CREATE EXTENSION IF NOT EXISTS pgcrypto;

TRUNCATE TABLE tarea RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario RESTART IDENTITY CASCADE;
TRUNCATE TABLE sector RESTART IDENTITY CASCADE;



-- Inserta sectores
INSERT INTO sector (nombre, descripcion, ubicacion)
VALUES
('Centro Santiago', 'Sector céntrico con alta densidad comercial y oficinas', ST_GeogFromText('SRID=4326;POINT(-70.6500 -33.4500)')),
('Las Condes', 'Zona residencial y empresarial', ST_GeogFromText('SRID=4326;POINT(-70.567 -33.416)')),
('San Miguel', 'Barrio mixto residencial-comercial', ST_GeogFromText('SRID=4326;POINT(-70.645 -33.485)')),
('Talca Centro', 'Área urbana con universidades y servicios', ST_GeogFromText('SRID=4326;POINT(-71.6667 -35.4333)')),
('Temuco Norte', 'Zona residencial en expansión', ST_GeogFromText('SRID=4326;POINT(-72.6000 -38.7000)')),
('Concepción Centro', 'Corazón de la ciudad universitaria y comercial', ST_GeogFromText('SRID=4326;POINT(-73.0500 -36.8200)'));