USE motorepuestos_sur;

CREATE TABLE devolucion (
    id_devolucion INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_venta INT NOT NULL,
    id_usuario INT NOT NULL,
    motivo VARCHAR(255) NOT NULL,

    CONSTRAINT fk_devolucion_venta
        FOREIGN KEY (id_venta)
        REFERENCES venta(id_venta),

    CONSTRAINT fk_devolucion_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);

CREATE TABLE detalle_devolucion (
    id_detalle_devolucion INT AUTO_INCREMENT PRIMARY KEY,
    id_devolucion INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    reintegra_stock BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_detalle_devolucion
        FOREIGN KEY (id_devolucion)
        REFERENCES devolucion(id_devolucion),

    CONSTRAINT fk_devolucion_producto
        FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto)
);

CREATE TABLE movimiento_stock (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo ENUM(
        'ENTRADA',
        'VENTA',
        'DEVOLUCION',
        'AJUSTE'
    ) NOT NULL,
    cantidad INT NOT NULL,
    stock_anterior INT NOT NULL,
    stock_nuevo INT NOT NULL,
    referencia VARCHAR(100),
    observacion VARCHAR(255),

    CONSTRAINT fk_movimiento_producto
        FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto),

    CONSTRAINT fk_movimiento_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);