package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import BLL.Pedido;
import BLL.Productos;
import singleton.Conexion;

public class ControllerPedido {
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

    public static void registrarPedido(Pedido pedido, int idCliente, String productosString, double total) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "INSERT INTO pedido (productos, fk_met_pago, fk_cliente, estado) VALUES (?, 1, ?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, productosString + " - Total: $" + String.format("%.2f", total));
            stmt.setInt(2, idCliente);
            stmt.setString(3, pedido.getEstado());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                pedido.setIdPedido(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Pedido registrado con éxito.\nProductos: " + productosString + "\nTotal: $" + String.format("%.2f", total));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar pedido: " + e.getMessage());
        }
    }

    public static String verEstadoPedido(int idPedido) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT productos, estado FROM pedido WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Pedido ID " + idPedido + ": " + rs.getString("productos") + ", Estado: " + rs.getString("estado");
            } else {
                return "Pedido no encontrado.";
            }
        } catch (SQLException e) {
            return "Error al buscar pedido: " + e.getMessage();
        }
    }

    public static String verHistorialPedidos(int idCliente) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_pedido, productos FROM pedido WHERE fk_cliente = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder("Historial de Pedidos:\n");
            boolean hayPedidos = false;
            while (rs.next()) {
                hayPedidos = true;
                sb.append("ID: ").append(rs.getInt("id_pedido"))
                  .append(", Productos: ").append(rs.getString("productos"))
                  .append("\n");
            }
            if (hayPedidos) {
                return sb.toString();
            } else {
                return "No hay pedidos";
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar historial: " + e.getMessage());
            return "No hay pedidos";
        }
    }
}