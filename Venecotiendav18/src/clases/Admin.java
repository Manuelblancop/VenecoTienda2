package clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import singleton.Conexion;

public class Admin extends Usuario {
    private int ID = 0;

    public Admin(String nombre, String pass, String rol, int iD) {
        super(nombre, pass, rol);
        ID = iD;
    }

    public void verPedidosAsignados() {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_pedido, productos, fk_cliente FROM pedido";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder("Pedidos:\n");
            boolean hayPedidos = false;
            while (rs.next()) {
                hayPedidos = true;
                sb.append("ID: ").append(rs.getInt("id_pedido"))
                  .append(", Productos: ").append(rs.getString("productos"))
                  .append(", Cliente ID: ").append(rs.getInt("fk_cliente"))
                  .append("\n");
            }
            if (hayPedidos) {
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No hay pedidos asignados. Por favor, agrega pedidos a la base de datos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar pedidos: " + e.getMessage());
        }
    }

    public void verProductos() {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_producto, nombre, descripcion, precio FROM producto";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder("Productos:\n");
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
                JOptionPane.showMessageDialog(null, "No hay productos disponibles. Por favor, agrega productos a la base de datos.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage());
        }
    }

    public void verEstadoPedidos() {
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

    public void eliminarProducto() {
        int idProducto = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el ID del producto a eliminar:"));
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "DELETE FROM producto WHERE id_producto = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, idProducto);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado.");
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void editarPerfil() {
        String nuevoNombre = JOptionPane.showInputDialog(null, "Nuevo nombre:");
        String nuevaPass = JOptionPane.showInputDialog(null, "Nueva contraseña:");
        if (nuevoNombre != null && nuevaPass != null && !nuevoNombre.isEmpty() && !nuevaPass.isEmpty()) {
            try {
                Connection conexion = Conexion.getInstance().getConnection();
                String query = "UPDATE usuario SET nombre_usuario = ?, password = ? WHERE id_usuario = ?";
                PreparedStatement stmt = conexion.prepareStatement(query);
                stmt.setString(1, nuevoNombre);
                stmt.setString(2, nuevaPass);
                stmt.setInt(3, getIdUsuario());
                stmt.executeUpdate();
                setNombre(nuevoNombre);
                setPass(nuevaPass);
                JOptionPane.showMessageDialog(null, "Perfil actualizado.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al actualizar perfil: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nombre y contraseña no pueden estar vacíos.");
        }
    }

    public void verMenu() {
        String[] opciones = {"Ver Pedidos Asignados", "Ver Productos", "Ver Estado de Pedidos", "Eliminar Producto", "Editar Perfil", "Cerrar Sesión"};
        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(null, "Menú del Admin", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (seleccion) {
                case 0:
                    verPedidosAsignados();
                    break;
                case 1:
                    verProductos();
                    break;
                case 2:
                    verEstadoPedidos();
                    break;
                case 3:
                    eliminarProducto();
                    break;
                case 4:
                    editarPerfil();
                    break;
                case 5:
                    cerrarSesion();
                    JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
                    break;
                default:
                    if (seleccion != JOptionPane.CLOSED_OPTION) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, seleccione una opción.");
                    }
                    break;
            }
        } while (seleccion != 5 && seleccion != JOptionPane.CLOSED_OPTION);
    }

    public int getID() { return ID; }
    public void setID(int iD) { ID = iD; }
}