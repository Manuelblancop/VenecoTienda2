package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import BLL.Productos;
import singleton.Conexion;

public class ControllerRepartidor {
    public static List<Productos> verProductos() {
        List<Productos> productos = new ArrayList<>();
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_producto, nombre, descripcion, precio FROM producto";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder("Productos:\n");
            boolean hayProductos = false;
            while (rs.next()) {
                hayProductos = true;
                Productos producto = new Productos(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        new String[]{""}
                );
                productos.add(producto);
                sb.append(producto.toString()).append("\n");
            }
            if (hayProductos) {
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No hay productos disponibles. Por favor, agrega productos a la base de datos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage());
        }
        return productos;
    }

    public static String verEstadoPedido(int idPedido) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT productos FROM pedido WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Estado del Pedido ID " + idPedido + ": " + rs.getString("productos");
            } else {
                return "Pedido no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al buscar pedido: " + e.getMessage();
        }
    }

    public static String editarPerfil1(int idUsuario, String nuevoNombre, String nuevaPass, int nuevoNumCel) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "UPDATE usuario SET nombre_usuario = ?, password = ? WHERE id_usuario = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevaPass);
            stmt.setInt(3, idUsuario);
            stmt.executeUpdate();
            query = "UPDATE repartidor SET num_cel = ? WHERE fk_usuario = ?";
            stmt = conexion.prepareStatement(query);
            stmt.setInt(1, nuevoNumCel);
            stmt.setInt(2, idUsuario);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return "Perfil actualizado.";
            } else {
                return "Repartidor no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al actualizar perfil: " + e.getMessage();
        }
    }

	public static String editarPerfil(int idUsuario, String nuevoNombre, String nuevaPass, int nuevoNumCel) {
		// TODO Auto-generated method stub
		return null;
	}
}
