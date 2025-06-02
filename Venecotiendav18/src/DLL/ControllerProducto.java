package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import clases.Productos;
import singleton.Conexion;

public class ControllerProducto {



    public static Productos buscarPorId(int id) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT * FROM producto WHERE id_producto = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Productos(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    new String[]{""}
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    public static void filtrarPorCategoria(String categoria) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT * FROM producto WHERE fk_categoria = (SELECT id_categoria FROM categoria WHERE nombre = ?)";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, categoria);
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder("Productos en " + categoria + ":\n");
            boolean hayProductos = false;
            while (rs.next()) {
                hayProductos = true;
                sb.append(rs.getString("nombre")).append("\n");
            }
            if (hayProductos) {
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No hay productos en la categoría " + categoria);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al filtrar: " + e.getMessage());
        }
    }
	
}
