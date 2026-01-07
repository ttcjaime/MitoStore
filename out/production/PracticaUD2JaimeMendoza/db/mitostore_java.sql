create database if not exists MitoStore;
--
use MitoStore;
--
create table discografica(
nombre varchar (250) not null,
id int primary key auto_increment,
pais varchar (250) not null,
sitio_web varchar (250) not null,
email_contacto varchar (250) not null,
telefono_contacto int not null
);
--
create table artista(
nombre varchar (250) not null,
id int primary key auto_increment,
genero varchar (250) not null,
pais varchar (250) not null,
id_discografica int not null,
foreign key (id_discografica) references discografica (id)
);
--
create table disco(
nombre varchar (250) not null,
id int primary key auto_increment,
genero varchar (250) not null,
precio int not null,
color varchar (250) not null,
canciones varchar (250) not null,
id_discografica int not null,
id_artista int not null,
fecha_lanzamiento date,
foreign key (id_discografica) references discografica (id),
foreign key (id_artista) references artista (id)
);
--
CREATE FUNCTION existeArtista(f_name varchar(50))
RETURNS BIT
BEGIN
RETURN EXISTS (
SELECT 1
FROM artista
WHERE nombre = f_name
);
END;
--
CREATE FUNCTION existeDisco(f_name varchar(50))
RETURNS BIT
BEGIN
RETURN EXISTS (
SELECT 1
FROM disco
WHERE nombre = f_name
);
END;
--
CREATE FUNCTION existeDiscografica(f_name varchar(50))
RETURNS BIT
BEGIN
RETURN EXISTS (
SELECT 1
FROM discografica
WHERE nombre = f_name
);
END;