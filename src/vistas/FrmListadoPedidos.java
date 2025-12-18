/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import dao.PedidoDAO;

/**
 * EXPLICACIÓN PARA PPT: OPTIMIZACIÓN DE INTERFAZ Y ACCESIBILIDAD
 * Se aplicaron colores sólidos (Azul y Rojo) con alto contraste para 
 * mejorar la visibilidad de las funciones administrativas.
 */
public class FrmListadoPedidos extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;

    public FrmListadoPedidos() {
        setTitle("SISTEMA GASTRONÓMICO - DASHBOARD DE PEDIDOS");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- ENCABEZADO (HEADER) ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(28, 35, 49)); // Azul oscuro profundo
        pnlHeader.setPreferredSize(new Dimension(1100, 80));
        pnlHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        
        JLabel lblTitulo = new JLabel("HISTORIAL GENERAL DE PEDIDOS");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        pnlHeader.add(lblTitulo);
        add(pnlHeader, BorderLayout.NORTH);

        // --- CONFIGURACIÓN DE TABLA ---
        String[] columnas = {"ID PEDIDO", "N° MESA", "FECHA / HORA REGISTRO", "TOTAL VENTA", "ESTADO"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        
        // Estilos de la Tabla (Letras Grandes)
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tabla.setRowHeight(40);
        
        // EXPLICACIÓN PPT: Centrado de DATOS y ENCABEZADOS
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Centramos las celdas de datos
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Centramos los títulos de las columnas (Headers)
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(230, 230, 230));
        header.setPreferredSize(new Dimension(100, 45));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        JScrollPane sp = new JScrollPane(tabla);
        sp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(sp, BorderLayout.CENTER);

        // --- PANEL DE BOTONES (PIE DE PÁGINA) ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        pnlBotones.setPreferredSize(new Dimension(1100, 100));
        pnlBotones.setBackground(Color.WHITE);

        // BOTÓN REFRESCAR (Azul Sólido Intenso)
        JButton btnRefrescar = new JButton("REFRESCAR BASE DE DATOS");
        btnRefrescar.setPreferredSize(new Dimension(320, 50));
        btnRefrescar.setBackground(new Color(0, 102, 204)); // Azul fuerte
        btnRefrescar.setForeground(Color.WHITE);           // Texto blanco
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnRefrescar.setOpaque(true);                      // Fuerza el color en Windows
        btnRefrescar.setBorderPainted(false);              // Quita el borde blanco
        btnRefrescar.setFocusPainted(false);
        pnlBotones.add(btnRefrescar);

        // BOTÓN BORRAR (Rojo Sólido Intenso)
        JButton btnBorrar = new JButton("VACIAR TODO EL HISTORIAL");
        btnBorrar.setPreferredSize(new Dimension(320, 50));
        btnBorrar.setBackground(new Color(204, 0, 0));     // Rojo fuerte
        btnBorrar.setForeground(Color.WHITE);              // Texto blanco
        btnBorrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBorrar.setOpaque(true);                         // Fuerza el color en Windows
        btnBorrar.setBorderPainted(false);                 // Quita el borde blanco
        btnBorrar.setFocusPainted(false);
        pnlBotones.add(btnBorrar);

        add(pnlBotones, BorderLayout.SOUTH);

        // --- EVENTOS ---
        btnRefrescar.addActionListener(e -> cargarDatos());
        
        btnBorrar.addActionListener(e -> {
            int opc = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que desea borrar permanentemente todo el historial?", 
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(opc == JOptionPane.YES_OPTION) {
                if(new PedidoDAO().borrarTodo()) {
                    cargarDatos();
                    JOptionPane.showMessageDialog(this, "Base de datos reseteada correctamente.");
                }
            }
        });

        cargarDatos();
    }

    public void cargarDatos() {
        modelo.setRowCount(0);
        PedidoDAO dao = new PedidoDAO();
        for (Object[] fila : dao.listarHistorial()) {
            modelo.addRow(fila);
        }
    }
}