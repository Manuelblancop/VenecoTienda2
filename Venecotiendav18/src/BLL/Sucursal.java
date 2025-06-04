package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import DLL.ControllerSucursal;
import singleton.Conexion;
import singleton.Menu;

public class Sucursal {
    private String ubicacion = "";
    private LinkedList<Menu> menu = new LinkedList<>();

    public Sucursal(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Productos> verProductos() {
        return ControllerSucursal.verProductos(ubicacion);
    }

    public void agregarProductoMenu() {
        List<Productos> productosDisponibles = verProductos();
        if (productosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos disponibles para agregar al menú.");
            return;
        }

        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del producto a agregar al menú:");
        try {
            int idProducto = Integer.parseInt(inputId);
            Productos productoSeleccionado = null;
            for (Productos p : productosDisponibles) {
                if (p.getID() == idProducto) {
                    productoSeleccionado = p;
                    break;
                }
            }
            if (productoSeleccionado != null) {
                String resultado = ControllerSucursal.agregarProductoMenu(productoSeleccionado, ubicacion);
                JOptionPane.showMessageDialog(null, resultado);
            } else {
                JOptionPane.showMessageDialog(null, "Producto con ID " + idProducto + " no encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void verMenuSucursal() {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio " +
                          "FROM producto p JOIN producto_sucursal ps ON p.id_producto = ps.id_producto " +
                          "WHERE ps.ubicacion_sucursal = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, ubicacion);
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder("Menú de la Sucursal " + ubicacion + ":\n");
            boolean hayProductos = false;
            while (rs.next()) {
                hayProductos = true;
                sb.append("ID: ").append(rs.getInt("id_producto"))
                  .append(", Nombre: ").append(rs.getString("nombre"))
                  .append(", Descripción: ").append(rs.getString("descripcion"))
                  .append(", Precio: $").append(rs.getDouble("precio"))
                  .append("\n");
            }
            if (hayProductos) {
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No hay productos en el menú de la sucursal.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar menú: " + e.getMessage());
        }
    }

    public void verMenu() {
        String[] opciones = {"Ver Productos", "Agregar Producto al Menú", "Ver Menú de la Sucursal", "Cerrar Sesión"};
        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(null, "Menú de la Sucursal", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (seleccion) {
                case 0:
                    verProductos();
                    break;
                case 1:
                    agregarProductoMenu();
                    break;
                case 2:
                    verMenuSucursal();
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
                    break;
                default:
                    if (seleccion != JOptionPane.CLOSED_OPTION) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, seleccione una opción.");
                    }
                    break;
            }
        } while (seleccion != 3 && seleccion != JOptionPane.CLOSED_OPTION);
    }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public LinkedList<Menu> getMenu() { return menu; }
    public void setMenu(LinkedList<Menu> menu) { this.menu = menu; }
}