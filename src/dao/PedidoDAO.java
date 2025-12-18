/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    ConexionBD cn = new ConexionBD();
    Connection con;
    PreparedStatement ps;

    // --- 1. NUEVO MÉTODO: GUARDAR (Esto quita el error en FrmMozo) ---
    // EXPLICACIÓN PPT: Implementación de la persistencia de datos (INSERT).
    public boolean guardarPedido(int idMesa, double total) {
        // Usamos los nombres de columnas según tu script SQL
        String sql = "INSERT INTO pedidos (id_mesa, total, estado_pedido) VALUES (?, ?, 'Pendiente')";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idMesa);
            ps.setDouble(2, total);
            
            // Retorna true si se insertó al menos una fila
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
            return false;
        } finally {
            // Cerramos recursos para optimizar la memoria
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // --- 2. MÉTODO PARA LISTAR (Para tu Dashboard Profesional) ---
    public List<Object[]> listarHistorial() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id_pedido, id_mesa, fecha_hora, total, estado_pedido FROM pedidos ORDER BY id_pedido DESC";
        try {
            con = cn.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lista.add(new Object[]{
                    rs.getInt("id_pedido"),
                    rs.getInt("id_mesa"),
                    rs.getTimestamp("fecha_hora"),
                    "S/ " + rs.getDouble("total"),
                    rs.getString("estado_pedido")
                });
            }
        } catch (SQLException e) { 
            System.out.println("Error al listar: " + e.getMessage()); 
        }
        return lista;
    }

    // --- 3. MÉTODO PARA BORRAR (Limpieza de registros) ---
    public boolean borrarTodo() {
        String sql = "DELETE FROM pedidos"; 
        String resetAi = "ALTER TABLE pedidos AUTO_INCREMENT = 1"; 
        try {
            con = cn.conectar();
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.executeUpdate(resetAi);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al borrar historial: " + e.getMessage());
            return false;
        }
    }
}