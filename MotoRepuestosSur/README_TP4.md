# MotoRepuestos Sur

Proyecto integrador de Seminario de Práctica Informática (TP1 a TP4).
Se mantiene el prototipo en Java 21, Swing y MySQL trabajado en las entregas anteriores.

## Ejecutar en NetBeans

1. Abrir la carpeta del proyecto (donde está `pom.xml`) en NetBeans.
2. Copiar `config.ejemplo.properties` como `config.local.properties` en esa misma carpeta.
3. Abrir `config.local.properties` y escribir la contraseña de tu MySQL local en `db.password`.
4. Verificar que exista la base `motorepuestos_sur` con las tablas del TP2.
5. Ejecutar **Clean and Build** y luego **Run**. Ingresar con un usuario registrado en la tabla `usuario`.

**Importante:** `config.local.properties` queda excluido de Git mediante `.gitignore`. No subir ese archivo ni contraseñas dentro del código. El archivo local tiene prioridad sobre las variables de entorno, para evitar conflictos con configuraciones anteriores.

## Organización y funcionamiento

- `LoginForm`, `MenuPrincipalForm`, `ProductosForm`, `VentasForm` y `DevolucionesForm`: pantallas Swing.
- `Producto`, `Usuario`, `Gerente`, `Vendedor` y `EncargadoStock`: clases del modelo y ejemplos de POO.
- `dao/ProductoDAO`: consulta, búsqueda y ordenación de productos mediante `ArrayList`.
- `dao/OperacionDAO`: registra ventas, devoluciones y movimientos de stock con transacciones JDBC.
- `servicio/InventarioServicio`: validaciones de las entradas y coordinación de las operaciones.
- `excepciones/OperacionInvalidaException`: errores propios de reglas del negocio.
- `ConexionBD`: conexión a MySQL, con contraseña almacenada solo en el equipo local.
- `Sesion`: identificador del usuario autenticado.

Se usan las tablas originales `producto`, `categoria`, `usuario`, `venta`, `detalle_venta`, `devolucion`, `detalle_devolucion` y `movimiento_stock`. **No se utiliza `operacion_tp4`** ni hace falta modificar las tablas existentes.

## Pruebas realizadas en el desarrollo

Se probaron manualmente el inicio de sesión, la consulta de productos, la búsqueda, la ordenación y la exportación CSV. También se registró una venta de prueba y una devolución con reintegro de stock; se verificó el resultado en MySQL Workbench. Se comprobaron mensajes de validación para una cantidad no numérica y para stock insuficiente.

En el código, las operaciones de escritura utilizan transacciones (`commit` y `rollback`). Una devolución exige indicar la venta original y no puede superar las unidades vendidas pendientes de devolución. Si no se marca **Reintegra stock**, se registra la devolución sin modificar las existencias.

Los objetos creados en `MotoRepuestosSur.main` sirven como ejemplos de arreglos, herencia y control de stock mínimo. No representan el inventario actual; el formulario Productos consulta MySQL.

## Consultas de control

```sql
SELECT id_venta, id_usuario, total FROM venta ORDER BY id_venta DESC LIMIT 5;
SELECT * FROM detalle_venta ORDER BY id_detalle_venta DESC LIMIT 5;
SELECT * FROM devolucion ORDER BY id_devolucion DESC LIMIT 5;
SELECT * FROM detalle_devolucion ORDER BY id_detalle_devolucion DESC LIMIT 5;
SELECT * FROM movimiento_stock ORDER BY id_movimiento DESC LIMIT 10;
SELECT codigo, stock_actual FROM producto ORDER BY codigo;
```

## Alcance

Es un prototipo académico operativo. Quedan para una versión posterior los permisos por rol dentro de cada pantalla y el uso de contraseñas de usuarios almacenadas con hash. No incluye pruebas automatizadas ni despliegue en red.
