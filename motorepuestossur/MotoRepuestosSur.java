package com.motorepuestossur;

public class MotoRepuestosSur {

    public static void main(String[] args) {

        Producto producto1 = new Producto(
                1,
                "FR001",
                "Pastillas de freno Honda CG 150",
                "Juego de pastillas de freno delantero",
                18500.00,
                15,
                5,
                true
        );
Producto producto2 = new Producto(
        2,
        "EN001",
        "Bujía NGK",
        "Bujía para moto",
        9500.00,
        3,
        5,
        true
);

Producto producto3 = new Producto(
        3,
        "FI001",
        "Filtro de aire",
        "Filtro de aire universal",
        12000.00,
        8,
        4,
        true
);

Producto[] productos = {producto1, producto2, producto3};

System.out.println("\nListado de productos:");

for (Producto producto : productos) {
    System.out.println(producto);

    if (producto.tieneStockBajo()) {
        System.out.println("ALERTA: producto con stock bajo.");
    }
}

System.out.println(producto1);
System.out.println("¿Tiene stock bajo? " + producto1.tieneStockBajo());

// Prueba de herencia y polimorfismo
System.out.println("\nPrueba de herencia y polimorfismo:");

Usuario[] usuarios = {
    new Gerente(1, "María", "Díaz", "mdiaz"),
    new Vendedor(2, "Juan", "Pérez", "jperez"),
    new EncargadoStock(3, "Pedro", "Gómez", "pgomez")
};

for (Usuario usuario : usuarios) {
    System.out.println("Rol: " + usuario.obtenerRol());
    usuario.mostrarPermisos();
}

java.awt.EventQueue.invokeLater(() -> {
    new LoginForm().setVisible(true);
});

    }
}