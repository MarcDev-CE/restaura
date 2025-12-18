/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

import javax.swing.*;
import java.awt.*;

public class FrmLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnEntrar;

    public FrmLogin() {
        // --- CONFIGURACIÓN DE VENTANA (Para tu PPT) ---
        // Definimos el título y centramos la ventana para mejorar la experiencia de usuario
        setTitle("ACCESO - SISTEMA DE RESTAURANTE");
        setSize(380, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245)); // Gris muy claro (Silver)

        // --- ENCABEZADO AZUL (Consistente con FrmMozo) ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(0, 102, 204)); // Azul Profesional
        pnlHeader.setBounds(0, 0, 380, 100);
        pnlHeader.setLayout(null);
        add(pnlHeader);

        JLabel lblLogin = new JLabel("CONTROL DE ACCESO");
        lblLogin.setForeground(Color.WHITE);
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogin.setBounds(70, 35, 250, 30);
        pnlHeader.add(lblLogin);

        // --- FORMULARIO DE CREDENCIALES ---
        Font fLabel = new Font("Segoe UI", Font.BOLD, 13);

        // Etiqueta de Usuario
        JLabel l1 = new JLabel("USUARIO / CORREO");
        l1.setBounds(40, 130, 200, 20);
        l1.setFont(fLabel);
        add(l1);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 155, 285, 40);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        // Agregamos un borde sutil para que se vea más limpio
        txtUsuario.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(txtUsuario);

        // Etiqueta de Contraseña
        JLabel l2 = new JLabel("CONTRASEÑA");
        l2.setBounds(40, 220, 200, 20);
        l2.setFont(fLabel);
        add(l2);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(40, 245, 285, 40);
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(txtPassword);

        // --- BOTÓN DE ENTRADA ---
        btnEntrar = new JButton("INGRESAR AL SISTEMA");
        btnEntrar.setBounds(40, 330, 285, 50);
        btnEntrar.setBackground(new Color(0, 102, 204));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR)); // El cursor cambia a "mano"
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        add(btnEntrar);

        // Acción al hacer clic
        btnEntrar.addActionListener(e -> validarAcceso());
    }

    private void validarAcceso() {
    // Extraemos el texto de los campos de la interfaz
    String user = txtUsuario.getText();
    // Convertimos el arreglo de caracteres de la clave a un String usable
    String pass = String.valueOf(txtPassword.getPassword());

    dao.UsuarioDAO dao = new dao.UsuarioDAO();

    if (dao.validarLogin(user, pass)) {
        JOptionPane.showMessageDialog(this, "Acceso concedido. ¡Bienvenido Marcos!");
        new FrmMozo().setVisible(true);
        this.dispose(); 
    } else {
        // Mensaje de feedback si la base de datos devuelve false
        JOptionPane.showMessageDialog(this, "Credenciales incorrectas o usuario no registrado.");
    }
}
}