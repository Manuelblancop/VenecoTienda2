package clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import singleton.Conexion;
public class Admin extends Usuario {
    private int ID = 0;

    public Admin(String nombre, String pass, String rol, int iD) {
        super(nombre, pass, rol);
        ID = iD;
    }

    public String verPedidosAsignados() {
        StringBuilder sb = new StringBuilder("📋 Pedidos:\n\n");
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT p.id_pedido, p.productos, p.fk_cliente, c.nombre AS nombre_cliente " +
                           "FROM pedido p " +
                           "JOIN cliente c ON p.fk_cliente = c.id_cliente";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            boolean hayPedidos = false;
            while (rs.next()) {
                hayPedidos = true;
                sb.append("🆔 Pedido: ").append(rs.getInt("id_pedido"))
                  .append(" | Cliente ID: ").append(rs.getInt("fk_cliente"))
                  .append(" (").append(rs.getString("nombre_cliente")).append(")")
                  .append("\n   🛒 Productos: ").append(rs.getString("productos"))
                  .append("\n\n");
            }
            return hayPedidos ? sb.toString() : "⚠️ No hay pedidos asignados.";
        } catch (SQLException e) {
            return "❌ Error al cargar pedidos: " + e.getMessage();
        }
    }

    public String verProductos() {
        StringBuilder sb = new StringBuilder("📦 Productos disponibles:\n\n");
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_producto, nombre, descripcion, precio FROM producto";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            boolean hayProductos = false;
            while (rs.next()) {
                hayProductos = true;
                sb.append("🆔 ").append(rs.getInt("id_producto"))
                  .append(" | ").append(rs.getString("nombre"))
                  .append(" ($").append(rs.getDouble("precio")).append(")")
                  .append("\n   ").append(rs.getString("descripcion"))
                  .append("\n\n");
            }
            return hayProductos ? sb.toString() : "⚠️ No hay productos disponibles.";
        } catch (SQLException e) {
            return "❌ Error al cargar productos: " + e.getMessage();
        }
    }

    public String verEstadoPedidos(int idPedido) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT productos FROM pedido WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "📦 Pedido ID " + idPedido + ": " + rs.getString("productos");
            } else {
                return "⚠️ Pedido no encontrado.";
            }
        } catch (SQLException e) {
            return "❌ Error al buscar pedido: " + e.getMessage();
        }
    }

    public String eliminarProducto(int idProducto) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "DELETE FROM producto WHERE id_producto = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idProducto);
            int rows = stmt.executeUpdate();
            return rows > 0 ? "🗑 Producto eliminado con éxito." : "⚠️ Producto no encontrado.";
        } catch (SQLException e) {
            return "❌ Error al eliminar producto: " + e.getMessage();
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
                return "✅ Perfil actualizado correctamente.";
            } catch (SQLException e) {
                return "❌ Error al actualizar perfil: " + e.getMessage();
            }
        } else {
            return "⚠️ Nombre y contraseña no pueden estar vacíos.";
        }
    }

    public void cerrarSesion() {
        // Lógica si necesitás, o simplemente dejarlo así
    }

    public int getID() { return ID; }
    public void setID(int iD) { ID = iD; }
}
