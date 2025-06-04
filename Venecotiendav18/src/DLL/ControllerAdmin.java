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

public class ControllerAdmin {
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

    public static String verEstadoPedidos(int idPedido) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT productos FROM pedido WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Pedido ID " + idPedido + ": " + rs.getString("productos");
            } else {
                return "Pedido no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al buscar pedido: " + e.getMessage();
        }
    }

    public static String eliminarProducto(int idProducto) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "DELETE FROM producto WHERE id_producto = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idProducto);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return "Producto eliminado.";
            } else {
                return "Producto no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al eliminar producto: " + e.getMessage();
        }
    }

    public static String editarPerfil(int idUsuario, String nuevoNombre, String nuevaPass) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "UPDATE usuario SET nombre_usuario = ?, password = ? WHERE id_usuario = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevaPass);
            stmt.setInt(3, idUsuario);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return "Perfil actualizado.";
            } else {
                return "Usuario no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al actualizar perfil: " + e.getMessage();
        }
    }

	public static List<Productos> verProductos1() {
		// TODO Auto-generated method stub
		return null;
	}
}
