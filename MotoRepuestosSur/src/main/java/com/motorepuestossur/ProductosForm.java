/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.motorepuestossur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author heinz
 */
public class ProductosForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ProductosForm.class.getName());

    /**
     * Creates new form ProductosForm
     */
public ProductosForm() {
    initComponents();
    setLocationRelativeTo(null);
    prepararHerramientas();
    cargarProductos();
}
private final com.motorepuestossur.dao.ProductoDAO productoDAO = new com.motorepuestossur.dao.ProductoDAO();
private final javax.swing.JTextField filtro = new javax.swing.JTextField(14);
private final javax.swing.JComboBox<String> orden = new javax.swing.JComboBox<>(new String[]{"Nombre", "Stock"});
private java.util.ArrayList<Producto> productos = new java.util.ArrayList<>();

private void prepararHerramientas() {
    javax.swing.JPanel herramientas = new javax.swing.JPanel();
    herramientas.add(new javax.swing.JLabel("Buscar:"));
    herramientas.add(filtro);
    herramientas.add(new javax.swing.JLabel("Ordenar:"));
    herramientas.add(orden);
    javax.swing.JButton exportar = new javax.swing.JButton("Exportar CSV");
    herramientas.add(exportar);
    java.awt.Container contenidoOriginal = getContentPane();
    javax.swing.JPanel contenedor = new javax.swing.JPanel(new java.awt.BorderLayout());
    setContentPane(contenedor);
    contenedor.add(contenidoOriginal, java.awt.BorderLayout.CENTER);
    contenedor.add(herramientas, java.awt.BorderLayout.SOUTH);
    filtro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { mostrarProductos(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { mostrarProductos(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { mostrarProductos(); }
    });
    orden.addActionListener(e -> mostrarProductos());
    exportar.addActionListener(e -> exportarCSV());
    pack();
}

private void cargarProductos() {
    try {
        productos = productoDAO.listar();
        mostrarProductos();
    } catch (SQLException ex) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error al consultar MySQL: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void mostrarProductos() {
    java.util.ArrayList<Producto> visibles = productoDAO.buscar(productos, filtro.getText());
    if (orden.getSelectedIndex() == 1) productoDAO.ordenarPorStock(visibles);
    else productoDAO.ordenarPorNombre(visibles);
    // La información se presenta desde el ArrayList y se actualiza al buscar y ordenar.
    javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(
        new String[]{"Código", "Producto", "Categoría", "Precio", "Stock actual", "Stock mínimo", "Estado"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    // Arreglo complementario al ArrayList: cada fila de la tabla.
    for (Producto p : visibles) {
        Object[] fila = {p.getCodigo(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getStockActual(),
                         p.getStockMinimo(), p.tieneStockBajo() ? "Stock bajo" : "Normal"};
        modelo.addRow(fila);
    }
    tablaProductos.setModel(modelo);
}

private void exportarCSV() {
    javax.swing.JFileChooser selector = new javax.swing.JFileChooser();
    selector.setSelectedFile(new java.io.File("productos.csv"));
    if (selector.showSaveDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION) return;
    try (java.io.BufferedWriter out = java.nio.file.Files.newBufferedWriter(
            selector.getSelectedFile().toPath(), java.nio.charset.StandardCharsets.UTF_8)) {
        out.write("codigo,nombre,categoria,precio,stock_actual,stock_minimo\n");
        for (Producto p : productoDAO.buscar(productos, filtro.getText())) {
            out.write(csv(p.getCodigo()) + "," + csv(p.getNombre()) + "," + csv(p.getCategoria()) + "," + p.getPrecio()
                + "," + p.getStockActual() + "," + p.getStockMinimo() + "\n");
        }
        javax.swing.JOptionPane.showMessageDialog(this, "Archivo CSV guardado correctamente.");
    } catch (java.io.IOException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
    }
}
private static String csv(String texto) {
    return "\"" + texto.replace("\"", "\"\"") + "\"";
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("GESTIÓN DE PRODUCTOS Y STOCK");

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código ", "Producto ", "Categoría ", "Precio ", "Stock"
            }
        ));
        jScrollPane1.setViewportView(tablaProductos);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(btnActualizar)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
     cargarProductos();
    }//GEN-LAST:event_btnActualizarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ProductosForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}
