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

public class ControllerDelivery {
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

    public static String aceptarPedido(int idPedido, int idRepartidor) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "UPDATE pedido SET fk_repartidor = ?, estado = 'Asignado' WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idRepartidor);
            stmt.setInt(2, idPedido);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return "Pedido asignado al repartidor.";
            } else {
                return "Pedido no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al asignar pedido: " + e.getMessage();
        }
    }

    public static String rechazarPedido(int idPedido) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "UPDATE pedido SET estado = 'Rechazado' WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idPedido);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return "Pedido rechazado.";
            } else {
                return "Pedido no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al rechazar pedido: " + e.getMessage();
        }
    }

    public static String verDetallesPedido(int idPedido) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT productos, fk_cliente, estado FROM pedido WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Pedido ID " + idPedido + ": Productos: " + rs.getString("productos") +
                       ", Cliente ID: " + rs.getInt("fk_cliente") + ", Estado: " + rs.getString("estado");
            } else {
                return "Pedido no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al buscar pedido: " + e.getMessage();
        }
    }

    public static String marcarEntregado(int idPedido) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "UPDATE pedido SET estado = 'Entregado' WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idPedido);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return "Pedido marcado como entregado.";
            } else {
                return "Pedido no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al marcar como entregado: " + e.getMessage();
        }
    }
}