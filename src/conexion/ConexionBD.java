/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    // Tus datos de conexión (puse la contraseña que vi en tu imagen)
    private final String baseDatos = "bd_restaurante";
    private final String url = "jdbc:mysql://localhost:3306/" + baseDatos;
    private final String user = "root";
    private final String password = "Vallejos1428"; // <--- ¡Ojo! Pon tu clave real aquí

    private Connection con = null;

    // Método para conectar
    public Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en conexión: " + e.getMessage());
        }
        return con;
    }

    // ---------------------------------------------------------
    // ESTE ES EL MAIN QUE TE FALTA PARA PODER PROBARLO
    // ---------------------------------------------------------
    public static void main(String[] args) {
        // 1. Creamos una instancia de nuestra propia clase
        ConexionBD test = new ConexionBD();
        
        // 2. Probamos el método conectar
        Connection c = test.conectar();
        
        // 3. Verificamos si funcionó
        if (c != null) {
            System.out.println("***********************************");
            System.out.println("¡CONEXIÓN EXITOSA A MYSQL!");
            System.out.println("Base de datos: bd_restaurante");
            System.out.println("***********************************");
        } else {
            System.out.println("***********************************");
            System.out.println("ERROR: No se pudo conectar.");
            System.out.println("Revisa si XAMPP/MySQL está prendido.");
            System.out.println("***********************************");
        }
    }
}