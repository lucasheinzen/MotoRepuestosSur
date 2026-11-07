package com.motorepuestossur;

import javax.swing.*;
import java.awt.*;

public class DevolucionesForm extends JFrame {

    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JCheckBox chkReintegraStock;
    private JButton btnRegistrar;
    private JButton btnVolver;

    public DevolucionesForm() {

        setTitle("Devoluciones");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel lblTitulo = new JLabel("DEVOLUCIONES");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblCodigo = new JLabel("Código producto:");
        JLabel lblCantidad = new JLabel("Cantidad:");

        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();

        chkReintegraStock = new JCheckBox("Reintegra stock");

        btnRegistrar = new JButton("Registrar devolución");
        btnVolver = new JButton("Volver");

        JPanel panel = new JPanel();
        panel.setLayout(null);

        lblTitulo.setBounds(145, 20, 150, 25);
        lblCodigo.setBounds(40, 75, 120, 25);
        txtCodigoProducto.setBounds(165, 75, 170, 25);

        lblCantidad.setBounds(40, 115, 120, 25);
        txtCantidad.setBounds(165, 115, 170, 25);

        chkReintegraStock.setBounds(165, 150, 150, 25);

        btnRegistrar.setBounds(110, 195, 180, 30);
        btnVolver.setBounds(155, 240, 100, 30);

        panel.add(lblTitulo);
        panel.add(lblCodigo);
        panel.add(txtCodigoProducto);
        panel.add(lblCantidad);
        panel.add(txtCantidad);
        panel.add(chkReintegraStock);
        panel.add(btnRegistrar);
        panel.add(btnVolver);

        add(panel);

        btnRegistrar.addActionListener(e -> registrarDevolucion());

        btnVolver.addActionListener(e -> dispose());
    }

    private void registrarDevolucion() {
        try {
            String venta = JOptionPane.showInputDialog(this,"ID de la venta original:");
            if (venta == null) return;
            String motivo = JOptionPane.showInputDialog(this,"Motivo de la devolución:");
            if (motivo == null) return;
            int stock = new com.motorepuestossur.servicio.InventarioServicio().devolver(
                txtCodigoProducto.getText(), txtCantidad.getText(),
                chkReintegraStock.isSelected(), venta, motivo);
            JOptionPane.showMessageDialog(this,
                "Devolución guardada en MySQL. Stock actual: " + stock);
            txtCantidad.setText("");
        } catch (com.motorepuestossur.excepciones.OperacionInvalidaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                "Validación", JOptionPane.WARNING_MESSAGE);
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "No se pudo registrar la devolución en MySQL: " + ex.getMessage(),
                "Base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}
