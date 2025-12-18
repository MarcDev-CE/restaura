/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FrmCocina extends JFrame implements Runnable {

    private JTextArea txtPedidos;
    private JLabel lblEstado;

    public FrmCocina() {
        setTitle("MÓDULO COCINA (SERVIDOR)");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(100, 100); // Aparece a la izquierda
        setLayout(new BorderLayout());

        // Encabezado
        JPanel pnlHead = new JPanel();
        pnlHead.setBackground(new Color(220, 53, 69)); // Rojo tipo Cocina/Alerta
        pnlHead.add(new JLabel("PANELES DE PEDIDOS ENTRANTE")).setForeground(Color.WHITE);
        add(pnlHead, BorderLayout.NORTH);

        // Área de Pedidos
        txtPedidos = new JTextArea();
        txtPedidos.setBackground(Color.BLACK);
        txtPedidos.setForeground(Color.CYAN);
        txtPedidos.setFont(new Font("Consolas", Font.BOLD, 16));
        txtPedidos.setEditable(false);
        add(new JScrollPane(txtPedidos), BorderLayout.CENTER);

        // Estado
        lblEstado = new JLabel(" 🟢 Servidor de Cocina Activo - Esperando pedidos...");
        add(lblEstado, BorderLayout.SOUTH);

        // LANZAR EL HILO DEL SERVIDOR
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void run() {
        try {
            // Creamos el servidor en el puerto 5000
            ServerSocket servidor = new ServerSocket(5000);
            while (true) {
                // Espera a que un mozo envíe algo
                Socket cliente = servidor.accept();
                DataInputStream entrada = new DataInputStream(cliente.getInputStream());
                
                // Leemos el mensaje del pedido
                String pedido = entrada.readUTF();
                
                // Lo mostramos en pantalla con la hora
                txtPedidos.append("🔔 NUEVO PEDIDO: " + pedido + "\n");
                txtPedidos.append("------------------------------------------\n");
                
                cliente.close();
            }
        } catch (Exception e) {
            txtPedidos.append("Error en Servidor: " + e.getMessage());
        }
    }
}