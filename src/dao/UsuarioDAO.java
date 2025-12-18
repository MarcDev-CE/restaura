/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD; // Importamos tu clase de conexión
import java.sql.*;

/**
 * CAPA DE DATOS: Gestiona la seguridad y acceso de usuarios.
 * Para tu PPT: Esta clase implementa el patrón DAO para separar 
 * la lógica de negocio del acceso a la base de datos.
 */
public class UsuarioDAO {
    // Dentro de la clase UsuarioDAO.java
public boolean registrarUsuario(String user, String pass) {
    // EXPLICACIÓN PPT: Sentencia SQL INSERT para persistencia de nuevos empleados
    String sql = "INSERT INTO usuarios (usuario, clave) VALUES (?, ?)";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, user.trim());
        ps.setString(2, pass.trim());
        int resultado = ps.executeUpdate(); // Ejecuta la inserción
        return resultado > 0;
    } catch (SQLException e) {
        System.out.println("Error al registrar: " + e.getMessage());
        return false;
    }
}
    // Objetos de conexión JDBC
    ConexionBD cn = new ConexionBD();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Valida las credenciales ingresadas contra la tabla 'usuarios'
     * @param user Correo electrónico (marcosce@admin.com)
     * @param pass Contraseña (937132369)
     * @return boolean True si los datos existen y coinciden
     */
    public boolean validarLogin(String user, String pass) {
        // EXPLICACIÓN PPT: Usamos consultas parametrizadas (?) para 
        // prevenir ataques de Inyección SQL.
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        
        try {
            con = cn.conectar(); // Establece la comunicación con MySQL
            ps = con.prepareStatement(sql);
            
            // Asignamos los valores recibidos de la interfaz
            ps.setString(1, user.trim()); 
            ps.setString(2, pass.trim());
            
            rs = ps.executeQuery();

            // EXPLICACIÓN PPT: rs.next() devuelve verdadero si el cursor
            // encuentra una fila que coincida con el usuario y clave.
            if (rs.next()) {
                return true; 
            }
        } catch (SQLException e) {
            System.out.println("Error en Login DAO: " + e.getMessage());
        } finally {
            // EXPLICACIÓN PPT: Cerramos la conexión para liberar recursos del servidor
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
        return false; // Si no hay coincidencias o hay error, deniega el acceso
    }
}