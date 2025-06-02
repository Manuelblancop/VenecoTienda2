package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import singleton.Conexion;

public class Cliente extends Usuario {
    private int ID = 0;
    private int numCel = 0;
    private String direccion = "";

    public Cliente(String nombre, String pass, String rol, int iD, int numCel, String direccion) {
        super(nombre, pass, rol);
        ID = iD;
        this.numCel = numCel;
        this.direccion = direccion;
    }

    public List<Productos> verProductos() {
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

    public void hacerPedido() {
        // Mostrar productos disponibles y obtener la lista
        List<Productos> productosDisponibles = verProductos();
        if (productosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos disponibles para hacer un pedido.");
            return;
        }

        // Permitir al usuario seleccionar productos
        List<Productos> productosSeleccionados = new ArrayList<>();
        double total = 0.0;
        StringBuilder resumenProductos = new StringBuilder();
        boolean continuarSeleccionando = true;

        while (continuarSeleccionando) {
            String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del producto a agregar (o '0' para terminar):");
            try {
                int idProducto = Integer.parseInt(inputId);
                if (idProducto == 0) {
                    continuarSeleccionando = false;
                    continue;
                }

                // Buscar el producto seleccionado
                Productos productoSeleccionado = null;
                for (Productos p : productosDisponibles) {
                    if (p.getID() == idProducto) {
                        productoSeleccionado = p;
                        break;
                    }
                }

                if (productoSeleccionado != null) {
                    productosSeleccionados.add(productoSeleccionado);
                    total += productoSeleccionado.getPrecio();
                    resumenProductos.append(productoSeleccionado.getNombre()).append(", ");
                } else {
                    JOptionPane.showMessageDialog(null, "Producto con ID " + idProducto + " no encontrado.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
            }
        }

        // Verificar si se seleccionaron productos
        if (productosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se seleccionaron productos. Pedido cancelado.");
            return;
        }

        // Quitar la última coma y espacio del resumen
        String productosString = resumenProductos.length() > 2 ? 
                                resumenProductos.substring(0, resumenProductos.length() - 2) : 
                                resumenProductos.toString();

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

    public void verEstadoPedido() {
        int idPedido = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el ID del pedido:"));
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT productos FROM pedido WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Pedido ID " + idPedido + ": " + rs.getString("productos"));
            } else {
                JOptionPane.showMessageDialog(null, "Pedido no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar pedido: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void verHistorialPedidos() {
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
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No hay pedidos en el historial.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar historial: " + e.getMessage());
        }
    }

    public void verMenu() {
        String[] opciones = {"Ver Productos", "Hacer Pedido", "Ver Estado de Pedido", "Ver Historial de Pedidos", "Cerrar Sesión"};
        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(null, "Menú del Cliente", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (seleccion) {
                case 0:
                    verProductos();
                    break;
                case 1:
                    hacerPedido();
                    break;
                case 2:
                    verEstadoPedido();
                    break;
                case 3:
                    verHistorialPedidos();
                    break;
                case 4:
                    cerrarSesion();
                    JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
                    break;
                default:
                    if (seleccion != JOptionPane.CLOSED_OPTION) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, seleccione una opción.");
                    }
                    break;
            }
        } while (seleccion != 4 && seleccion != JOptionPane.CLOSED_OPTION);
    }

    public int getID() { return ID; }
    public void setID(int iD) { ID = iD; }
    public int getNumCel() { return numCel; }
    public void setNumCel(int numCel) { this.numCel = numCel; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}