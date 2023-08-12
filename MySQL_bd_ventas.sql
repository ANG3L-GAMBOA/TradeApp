create database bd_ventas;
-- drop database bd_ventas;
use bd_ventas;

create table tb_personal(
	dni char(8) not null primary key,
	nombre varchar(20) not null,
	ap_paterno varchar(20) not null,
    ap_materno varchar(20) not null,
	genero char(1));

insert into tb_personal values
('40414243', 'Carla', 'Ramos', 'Flores', 'F'),
('40414244', 'Raúl', 'Torres', 'Mejía', 'M');

select * from tb_personal;

delimiter $$
create procedure sp_ListarPersonal()
begin
	select * from tb_personal
	order by ap_paterno asc;
end $$

call sp_ListarPersonal();

-- Create table tb_marca
create table tb_marca (
	codigo_marca char(5) not null primary key,
    marca varchar(30) not null
);

-- Registrar datos en Marcas
insert into tb_marca values
('M0001', 'Sony'),
('M0002', 'LG');

select * from tb_marca;

-- Listar Marcas --procedimiento almacenado
delimiter $$
create procedure sp_listar_marca()
begin
	select * from tb_marca order by marca asc;
end; $$

call sp_listar_marca();

-- create table tb_categoria
create table tb_categoria (
	codigo_categoria char(5) not null primary key,
    categoria varchar(40) not null
);

-- Registrar datos en Categorías
insert into tb_categoria values
('C0001', 'Cómputo'),
('C0002', 'Electrodomésticos');

select * from tb_categoria;

-- Listar Categorías --prodedimiento almacenado
delimiter $$
create procedure sp_listar_categoria()
begin
	select * from tb_categoria order by categoria asc;
end; $$

call sp_listar_categoria();

-- drop table tb_producto

-- create table tb_producto
create table tb_producto (
	codigo_producto char(5) not null primary key,
    producto varchar(40) not null,
    costo float,
    producto_codigo_marca char(5) not null,
    producto_codigo_categoria char(5) not null
);

-- Registrar datos en Productos
insert into tb_producto values
('P0001', 'Lavadoraa 13 Kg.', 1365.28, 'M0002', 'C0002');

insert into tb_producto values
('P0002', 'PC Gamer Core i7 16GB', 2865.28, 'M0002', 'C0001');

select * from tb_producto;
-- drop procedure sp_listar_producto
-- Listar Producto --procedimiento almacenado
delimiter $$
create procedure sp_listar_producto()
begin
	select tb1.codigo_producto, tb1.producto, 
		   tb1.costo, tb2.marca, tb3.categoria
    from tb_producto tb1 inner join tb_marca tb2 on
    (tb1.producto_codigo_marca = tb2.codigo_marca) inner join tb_categoria tb3 on
    (tb1.producto_codigo_categoria = tb3.codigo_categoria)
    order by tb1.producto asc;
end; $$

call sp_listar_producto();

create table tb_inventario (
    id_prod varchar(50) not null primary key,
    vendido varchar(50) not null,
    disponible varchar(50) not null
);

insert into tb_inventario values
('P0001', '20', '30'),
('P0002', '22', '30');

select * from tb_inventario;

delimiter $$
create procedure sp_listar_inventario()
begin
    select tbp.producto, tbi.disponible, tbi.vendido
    from tb_inventario tbi
    inner join tb_producto tbp on tbi.id_prod = tbp.codigo_producto
    order by tbp.producto asc;
end; $$

create table tb_orden(
    id_orden char(5) primary key,
    id_producto char(5),
    id_usuario char(8),
    cantidad varchar(50)
);

insert into tb_orden values('O0001', 'P0001', '40414243', '30')
select * from tb_orden

delimiter $$
create procedure sp_listar_ordenes()
begin
    select orden.id_orden, 
    producto.producto, 
    CONCAT(usuario.nombre, ' ', usuario.ap_paterno) as 'Usuario', 
    orden.cantidad
    from tb_orden orden inner join tb_producto producto
    on orden.id_producto = producto.codigo_producto
    inner join tb_personal usuario
    on usuario.dni = orden.id_usuario;
end; $$

call sp_listar_orden();
