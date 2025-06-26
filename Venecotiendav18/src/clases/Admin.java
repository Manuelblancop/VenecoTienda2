// Archivo: Admin.java
package clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import singleton.Conexion;

public class Admin extends Usuario {
    private int ID;

    public Admin(String nombre, String pass, String rol, int iD) {
        super(nombre, pass, rol);
        this.ID = iD;
    }

    
    public String verPedidosAsignados() {
        StringBuilder sb = new StringBuilder("\uD83D\uDCCB Pedidos:\n----------------------------------\n\n");
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT p.id_pedido, p.productos, p.fk_cliente, c.nombre AS nombre_cliente, " +
                           "r.nombre AS nombre_repartidor " +
                           "FROM pedido p " +
                           "JOIN cliente c ON p.fk_cliente = c.id_cliente " +
                           "LEFT JOIN repartidor r ON p.fk_repartidor_asignado = r.id_repartidor";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            boolean hayPedidos = false;
            while (rs.next()) {
                hayPedidos = true;
                sb.append("\uD83C\uDD94 Pedido: ").append(rs.getInt("id_pedido"))
                  .append(" | Cliente ID: ").append(rs.getInt("fk_cliente"))
                  .append(" (").append(rs.getString("nombre_cliente")).append(")")
                  .append("\n   \uD83D\uDED2 Productos: ").append(rs.getString("productos"))
                  .append("\n   \uD83D\uDE9A Repartidor: ").append(rs.getString("nombre_repartidor") != null ? rs.getString("nombre_repartidor") : "No asignado")
                  .append("\n\n");
            }
            return hayPedidos ? sb.toString() : "\u26A0\uFE0F No hay pedidos asignados.";
        } catch (SQLException e) {
            return "\u274C Error al cargar pedidos: " + e.getMessage();
        }
    }
    public String verProductos() {
        StringBuilder sb = new StringBuilder("\uD83D\uDCE6 Productos disponibles:\n----------------------------------\n\n");
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_producto, nombre, descripcion, precio FROM producto";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            boolean hayProductos = false;
            while (rs.next()) {
                hayProductos = true;
                sb.append("\uD83C\uDD94 ").append(rs.getInt("id_producto"))
                  .append(" | ").append(rs.getString("nombre"))
                  .append(" ($").append(rs.getDouble("precio")).append(")")
                  .append("\n   ").append(rs.getString("descripcion"))
                  .append("\n\n");
            }
            return hayProductos ? sb.toString() : "\u26A0\uFE0F No hay productos disponibles.";
        } catch (SQLException e) {
            return "\u274C Error al cargar productos: " + e.getMessage();
        }
    }

    public String verEstadoPedidos() {
        StringBuilder sb = new StringBuilder("\uD83D\uDCCB Lista de pedidos:\n----------------------------------\n\n");
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT p.id_pedido, p.productos, cl.nombre AS cliente, e.nombre AS estado " +
                           "FROM pedido p JOIN cliente cl ON p.fk_cliente = cl.id_cliente " +
                           "JOIN estado_pedido e ON p.fk_estado_pedido = e.Id_Estado";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            boolean hayPedidos = false;
            while (rs.next()) {
                hayPedidos = true;
                sb.append("\uD83D\uDCE6 Pedido ID ").append(rs.getInt("id_pedido"))
                  .append("\n\uD83D\uDC64 Cliente: ").append(rs.getString("cliente"))
                  .append("\n\uD83D\uDED2 Productos: ").append(rs.getString("productos"))
                  .append("\n\uD83D\uDCCC Estado: ").append(rs.getString("estado"))
                  .append("\n\n");
            }
            return hayPedidos ? sb.toString() : "\u26A0\uFE0F No hay pedidos registrados.";
        } catch (SQLException e) {
            return "\u274C Error al obtener los pedidos: " + e.getMessage();
        }
    }

    public List<Productos> getProductos() {
        List<Productos> productos = new ArrayList<>();
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_producto, nombre, descripcion, precio FROM producto";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(new Productos(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    new String[]{"Comida"}
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public String editarProducto(int idProducto, String nuevoNombre, String nuevaDescripcion, double nuevoPrecio) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "UPDATE producto SET nombre = ?, descripcion = ?, precio = ? WHERE id_producto = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevaDescripcion);
            stmt.setDouble(3, nuevoPrecio);
            stmt.setInt(4, idProducto);
            int rows = stmt.executeUpdate();
            return rows > 0 ? "\u270F\uFE0F Producto actualizado con \u00e9xito." : "\u26A0\uFE0F Producto no encontrado.";
        } catch (SQLException e) {
            return "\u274C Error al editar producto: " + e.getMessage();
        }
    }

    public String editarPerfil(String nuevoNombre, String nuevaPass) {
        if (nuevoNombre != null && nuevaPass != null && !nuevoNombre.isEmpty() && !nuevaPass.isEmpty()) {
            try {
                Connection conexion = Conexion.getInstance().getConnection();
                String query = "UPDATE usuario SET nombre_usuario = ?, password = ? WHERE id_usuario = ?";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, nuevoNombre);
                stmt.setString(2, nuevaPass);
                stmt.setInt(3, getID());
                stmt.executeUpdate();
                setNombre(nuevoNombre);
                setPass(nuevaPass);
                return "\u2705 Perfil actualizado correctamente.";
            } catch (SQLException e) {
                return "\u274C Error al actualizar perfil: " + e.getMessage();
            }
        } else {
            return "\u26A0\uFE0F Nombre y contrase\u00f1a no pueden estar vac\u00edos.";
        }
    }

    public void cerrarSesion() {
        // Cerrar sesión
    }

    public int getID() { return ID; }
    public void setID(int iD) { this.ID = iD; }
}
