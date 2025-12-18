/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import dao.PlatoDAO;
import modelos.Plato;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class FrmMozo extends JFrame {

    private JTextField txtMesa, txtCant;
    private JComboBox<String> cboPlatos;
    private JButton btnEnviar, btnHistorial;
    private JTextArea txtLog;
    private vistas.FrmCocina cocina = null; 

    public FrmMozo() {
        setTitle("SISTEMA RESTAURANTE - MÓDULO MOZO");
        setSize(450, 720); // Aumentamos ligeramente el alto para el texto más grande
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 244, 247));

        // --- DEFINICIÓN DE FUENTES (MÁS GRANDES) ---
        Font fLabels = new Font("Segoe UI", Font.BOLD, 15); // Antes 13
        Font fInputs = new Font("Segoe UI", Font.PLAIN, 15); 
        Font fBotones = new Font("Segoe UI", Font.BOLD, 17); // Antes 16

        // HEADER
        JPanel header = new JPanel();
        header.setBackground(new Color(28, 35, 49)); 
        header.setBounds(0, 0, 450, 70);
        header.setLayout(null);
        add(header);

        JLabel lblTitulo = new JLabel("NUEVO PEDIDO");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBounds(25, 20, 300, 30);
        header.add(lblTitulo);

        // CARD CONTENEDOR
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBounds(20, 90, 395, 310); // Aumentamos altura a 310
        card.setLayout(null);
        card.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        add(card);

        // Mesa
        JLabel l1 = new JLabel("NÚMERO DE MESA");
        l1.setBounds(20, 20, 200, 25);
        l1.setFont(fLabels);
        card.add(l1);

        txtMesa = new JTextField();
        txtMesa.setBounds(20, 50, 120, 40); // Altura aumentada a 40
        txtMesa.setFont(fInputs);
        card.add(txtMesa);

        // Plato
        JLabel l2 = new JLabel("SELECCIONAR PLATILLO");
        l2.setBounds(20, 105, 250, 25);
        l2.setFont(fLabels);
        card.add(l2);

        cboPlatos = new JComboBox<>();
        cboPlatos.setBounds(20, 135, 350, 40); // Altura aumentada a 40
        cboPlatos.setFont(fInputs);
        card.add(cboPlatos);

        // Cantidad
        JLabel l3 = new JLabel("CANTIDAD");
        l3.setBounds(20, 195, 100, 25);
        l3.setFont(fLabels);
        card.add(l3);

        txtCant = new JTextField("1");
        txtCant.setBounds(20, 225, 100, 40); // Altura aumentada a 40
        txtCant.setFont(fInputs);
        card.add(txtCant);

        // --- BOTÓN ENVIAR ---
        btnEnviar = new JButton("ENVIAR A COCINA");
        btnEnviar.setBounds(20, 415, 395, 55);
        btnEnviar.setBackground(new Color(0, 102, 204));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(fBotones);
        btnEnviar.setOpaque(true);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnEnviar);

        // --- TERMINAL VERDE (TEXTO A 13) ---
        txtLog = new JTextArea();
        txtLog.setBackground(new Color(30, 30, 30));
        txtLog.setForeground(new Color(0, 255, 64));
        txtLog.setFont(new Font("Consolas", Font.PLAIN, 13)); // Aumentado a 13
        txtLog.setEditable(false);
        JScrollPane sp = new JScrollPane(txtLog);
        sp.setBounds(20, 485, 395, 75);
        add(sp);

        // --- BOTÓN HISTORIAL ---
        btnHistorial = new JButton("VER HISTORIAL DE PEDIDOS");
        btnHistorial.setBounds(20, 575, 395, 50);
        btnHistorial.setBackground(new Color(44, 62, 80)); 
        btnHistorial.setForeground(Color.WHITE);
        btnHistorial.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnHistorial.setOpaque(true);
        btnHistorial.setBorderPainted(false);
        btnHistorial.setFocusPainted(false);
        btnHistorial.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnHistorial);

        // ACCIONES
        cargarPlatos();
        btnEnviar.addActionListener(e -> enviarPedido());
        btnHistorial.addActionListener(e -> {
            new vistas.FrmListadoPedidos().setVisible(true);
        });
    }

    private void cargarPlatos() {
        try {
            dao.PlatoDAO platoDao = new dao.PlatoDAO();
            List<Plato> lista = platoDao.listar(); 
            cboPlatos.removeAllItems();
            for (Plato p : lista) {
                cboPlatos.addItem(p.getId() + " - " + p.getNombre() + " (S/ " + p.getPrecio() + ")");
            }
        } catch (Exception e) {
            txtLog.append("ERROR DB: " + e.getMessage() + "\n");
        }
    }

    private void enviarPedido() {
        if (cocina == null || !cocina.isVisible()) {
            cocina = new vistas.FrmCocina();
            cocina.setVisible(true);
            try { Thread.sleep(500); } catch (Exception ex) {}
        }

        String mesaStr = txtMesa.getText();
        String platoSeleccionado = (String) cboPlatos.getSelectedItem();
        String cant = txtCant.getText();
        String mensaje = "Mesa " + mesaStr + " | " + platoSeleccionado + " | Cant: " + cant;

        try {
            java.net.Socket socket = new java.net.Socket("127.0.0.1", 5000);
            java.io.DataOutputStream salida = new java.io.DataOutputStream(socket.getOutputStream());
            salida.writeUTF(mensaje);
            salida.close();
            socket.close();
            txtLog.append("✅ RED: Pedido enviado a cocina con éxito.\n");
        } catch (Exception e) {
            txtLog.append("❌ RED: Error de conexión -> " + e.getMessage() + "\n");
        }

        try {
            int idMesa = Integer.parseInt(mesaStr);
            double precioSimulado = 30.00; 
            dao.PedidoDAO pDao = new dao.PedidoDAO();
            
            if (pDao.guardarPedido(idMesa, precioSimulado)) {
                txtLog.append("✅ DB: Pedido registrado en MySQL.\n");
            } else {
                txtLog.append("⚠️ DB: No se pudo guardar. Revisa la mesa " + idMesa + ".\n");
            }
            
            txtMesa.setText("");
            txtMesa.requestFocus();

        } catch (NumberFormatException nfe) {
            txtLog.append("❌ ERROR: La mesa debe ser un número.\n");
        } catch (Exception e) {
            txtLog.append("❌ DB ERROR: " + e.getMessage() + "\n");
        }
    }
}