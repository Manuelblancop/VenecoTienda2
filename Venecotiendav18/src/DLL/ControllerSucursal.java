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

public class ControllerSucursal {
    public static List<Productos> verProductos(String ubicacion) {
        List<Productos> productos = new ArrayList<>();
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio " +
                          "FROM producto p JOIN producto_sucursal ps ON p.id_producto = ps.id_producto " +
                          "WHERE ps.ubicacion_sucursal = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, ubicacion);
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
                JOptionPane.showMessageDialog(null, "No hay productos disponibles en la sucursal.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage());
        }
        return productos;
    }

    public static String agregarProductoMenu(Productos producto, String ubicacion) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "INSERT INTO producto_sucursal (id_producto, ubicacion_sucursal) VALUES (?, ?)";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, producto.getID());
            stmt.setString(2, ubicacion);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return "Producto agregado al menú de la sucursal.";
            } else {
                return "Error al agregar producto.";
            }
        } catch (SQLException e) {
            return "Error al agregar producto: " + e.getMessage();
        }
    }
}
