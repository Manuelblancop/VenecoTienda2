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

public class ControllerCliente {

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
    public static void hacerPedido(int ID, String productosString, double total) {
    	// Registrar el pedido en la base de datos
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "INSERT INTO pedido (productos, fk_met_pago, fk_cliente) VALUES (?, 1, ?)";
            PreparedStatement stmt = conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, productosString + " - Total: $" + String.format("%.2f", total));
            stmt.setInt(2, ID);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pedido realizado con éxito.\nProductos: " + productosString + "\nTotal: $" + String.format("%.2f", total));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al hacer pedido: " + e.getMessage());
        }
    }

    public static String verHistorialPedidos(int ID) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_pedido, productos FROM pedido WHERE fk_cliente = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, ID);
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
        }
        return "No hay pedidos";

    }
}