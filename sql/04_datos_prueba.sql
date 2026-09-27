USE motorepuestos_sur;

INSERT INTO categoria (nombre, descripcion) VALUES
('Frenos', 'Pastillas, discos y componentes del sistema de frenos'),
('Encendido', 'Bujias, bobinas y componentes de encendido'),
('Filtros', 'Filtros de aire, aceite y combustible'),
('Aceites y lubricantes', 'Aceites y productos para lubricacion'),
('Accesorios', 'Accesorios generales para motocicletas'),
('Sistemas electricos', 'Baterias, reguladores y componentes electricos');

INSERT INTO producto
(codigo, nombre, descripcion, precio, stock_actual, stock_minimo, id_categoria)
VALUES
('FR001', 'Pastillas de freno Honda CG 150',
 'Juego de pastillas de freno delantero', 18500.00, 15, 5, 1),

('FR002', 'Disco de freno Honda Wave',
 'Disco de freno delantero', 42000.00, 7, 3, 1),

('EN001', 'Bujia NGK CPR8EA-9',
 'Bujia para motocicletas Honda', 8500.00, 25, 8, 2),

('FI001', 'Filtro de aceite Honda',
 'Filtro de aceite compatible con diferentes modelos Honda',
 7200.00, 4, 5, 3),

('AC001', 'Aceite 10W40 1L',
 'Aceite semisintetico para motocicletas', 14500.00, 20, 6, 4),

('EL001', 'Bateria 12V 7Ah',
 'Bateria para motocicleta 12V', 65000.00, 2, 3, 6);

INSERT INTO proveedor
(nombre, contacto, telefono, email, direccion)
VALUES
('Distribuidora Patagonia',
 'Carlos Martinez',
 '2984123456',
 'ventas@distribuidorapatagonia.com',
 'General Roca, Rio Negro'),

('Repuestos del Sur',
 'Mariana Lopez',
 '2994556677',
 'pedidos@repuestosdelsur.com',
 'Neuquen, Neuquen');

INSERT INTO usuario
(nombre, apellido, username, password, rol)
VALUES
('Mario', 'Diaz', 'mdiaz', 'admin123', 'GERENTE'),
('Ana', 'Lopez', 'alopez', 'stock123', 'STOCK'),
('Juan', 'Perez', 'jperez', 'venta123', 'VENDEDOR');

INSERT INTO cliente
(nombre, apellido, telefono, email)
VALUES
('Martin', 'Gonzalez', '2984332211', 'martin@email.com'),
('Sofia', 'Fernandez', '2984778899', 'sofia@email.com'),
('Diego', 'Rodriguez', '2984112233', NULL);