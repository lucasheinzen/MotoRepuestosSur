Base de datos de MotoRepuestos Sur
El archivo motorepuestos_sur_GITHUB.sql contiene la estructura y datos de demostracion de MySQL 8 para el proyecto TP4. Incluye 12 tablas, productos de prueba, ventas, devoluciones y movimientos de stock.
Importacion
En MySQL Workbench, usar Server > Data Import, seleccionar Import from Self-Contained File y elegir el SQL. Alternativamente, ejecutar el script en una base de prueba.

Aviso: el script usa CREATE DATABASE IF NOT EXISTS y DROP TABLE IF EXISTS. Importarlo sobre una instalacion existente puede eliminar datos de las tablas incluidas. Hacerlo solamente en una instancia/base de demostracion o con copia de seguridad previa.
Usuarios exclusivamente de demostracion
Usuario	Contraseña demo	Rol
mdiaz	DemoGerenteTP4_2026	GERENTE
alopez	DemoStockTP4_2026	STOCK
jperez	DemoVendedorTP4_2026	VENDEDOR


Estas credenciales no corresponden a la base local del desarrollador: fueron reemplazadas en esta copia publica. En el prototipo las contraseñas se comparan como texto plano; para produccion se requeriria autenticacion segura con hashes y manejo de secretos.

Los telefonos y correos de clientes/proveedores tambien se reemplazaron por ejemplos. Los datos de inventario, ventas y devoluciones se conservaron para que se puedan verificar las pruebas.
