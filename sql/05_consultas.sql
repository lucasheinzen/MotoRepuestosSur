USE motorepuestos_sur;

-- =====================================================
-- CONSULTAS SQL - SISTEMA MOTOREPUESTOS SUR
-- =====================================================


-- =====================================================
-- 1. CONSULTA GENERAL DE PRODUCTOS
-- Muestra los productos junto con su categoria,
-- precio y niveles de stock.
-- =====================================================

SELECT
    p.id_producto,
    p.codigo,
    p.nombre AS producto,
    c.nombre AS categoria,
    p.precio,
    p.stock_actual,
    p.stock_minimo
FROM producto p
INNER JOIN categoria c
    ON p.id_categoria = c.id_categoria
WHERE p.activo = TRUE
ORDER BY p.nombre;


-- =====================================================
-- 2. CONSULTA DE PRODUCTOS CON STOCK BAJO
-- Permite identificar productos cuyo stock actual
-- es menor o igual al minimo configurado.
-- =====================================================

SELECT
    codigo,
    nombre,
    stock_actual,
    stock_minimo
FROM producto
WHERE stock_actual <= stock_minimo
  AND activo = TRUE
ORDER BY stock_actual ASC;


-- =====================================================
-- 3. INSERCION DE UN PRODUCTO
-- Ejemplo de alta de un nuevo producto.
-- =====================================================

INSERT INTO producto
(
    codigo,
    nombre,
    descripcion,
    precio,
    stock_actual,
    stock_minimo,
    id_categoria
)
VALUES
(
    'EN002',
    'Bobina de encendido Honda CG 150',
    'Bobina de encendido para Honda CG 150',
    28000.00,
    10,
    3,
    2
);


-- Consulta utilizada para comprobar la insercion.

SELECT *
FROM producto
WHERE codigo = 'EN002';


-- =====================================================
-- 4. ACTUALIZACION DE UN PRODUCTO
-- Se modifica el precio del producto anteriormente
-- registrado.
-- =====================================================

UPDATE producto
SET precio = 29500.00
WHERE codigo = 'EN002';


-- Consulta utilizada para comprobar la modificacion.

SELECT
    codigo,
    nombre,
    precio
FROM producto
WHERE codigo = 'EN002';


-- =====================================================
-- 5. INSERCION DE UN REGISTRO TEMPORAL
-- Se crea un cliente exclusivamente para demostrar
-- posteriormente una operacion DELETE.
-- =====================================================

INSERT INTO cliente
(
    nombre,
    apellido,
    telefono,
    email
)
VALUES
(
    'Cliente',
    'Temporal',
    '0000000000',
    'temporal@prueba.com'
);


-- Consulta para comprobar que el registro fue creado.

SELECT *
FROM cliente
WHERE email = 'temporal@prueba.com';


-- =====================================================
-- 6. BORRADO FISICO DE UN REGISTRO
-- Se utiliza la clave primaria para identificar
-- de manera univoca el registro que sera eliminado.
-- IMPORTANTE: reemplazar 4 por el ID obtenido en
-- la consulta anterior si fuera diferente.
-- =====================================================

DELETE FROM cliente
WHERE id_cliente = 4;


-- Consulta para comprobar que el registro fue eliminado.

SELECT *
FROM cliente
WHERE id_cliente = 4;


-- =====================================================
-- 7. EJEMPLO DE BAJA LOGICA
-- En las entidades que requieren conservar historial,
-- como producto, se utiliza una baja logica en lugar
-- de eliminar fisicamente el registro.
--
-- Esta sentencia se presenta como ejemplo y no debe
-- ejecutarse sobre los datos de prueba actuales.
-- =====================================================

-- UPDATE producto
-- SET activo = FALSE
-- WHERE id_producto = ?;