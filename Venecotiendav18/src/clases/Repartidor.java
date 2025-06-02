package clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import singleton.Conexion;

public class Repartidor extends Usuario {
    private int ID = 0;
    private int numCell = 0;

    public Repartidor(String nombre, String pass, String rol, int iD, int numCell) {
        super(nombre, pass, rol);
        ID = iD;
        this.numCell = numCell;
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
                JOptionPane.showMessageDialog(null, "No hay pedidos asignados.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar pedidos: " + e.getMessage());
        }
    }

    public void verHistorialPedidos() {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_pedido, productos FROM pedido";
            PreparedStatement stmt = conexion.prepareStatement(query);
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
                JOptionPane.showMessageDialog(null, "No hay historial.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar historial: " + e.getMessage());
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
                JOptionPane.showMessageDialog(null, "Estado del Pedido ID " + idPedido + ": " + rs.getString("productos"));
            } else {
                JOptionPane.showMessageDialog(null, "Pedido no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar pedido: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void verMapa() {
        JOptionPane.showMessageDialog(null, "Mapa mostrado para " + getNombre());
    }

    public void editarPerfil() {
        String nuevoNombre = JOptionPane.showInputDialog(null, "Nuevo nombre:");
        String nuevaPass = JOptionPane.showInputDialog(null, "Nueva contraseña:");
        int nuevoNumCell = Integer.parseInt(JOptionPane.showInputDialog(null, "Nuevo número de celular:"));
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
                numCell = nuevoNumCell;
                JOptionPane.showMessageDialog(null, "Perfil actualizado.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al actualizar perfil: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nombre y contraseña no pueden estar vacíos.");
        }
    }

    public void verMenu() {
        String[] opciones = {"Ver Pedidos Asignados", "Ver Historial", "Ver Estado de Pedido", "Ver Mapa", "Editar Perfil", "Cerrar Sesión"};
        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(null, "Menú del Repartidor", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (seleccion) {
                case 0:
                    verPedidosAsignados();
                    break;
                case 1:
                    verHistorialPedidos();
                    break;
                case 2:
                    verEstadoPedido();
                    break;
                case 3:
                    verMapa();
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
    public int getNumCell() { return numCell; }
    public void setNumCell(int numCell) { this.numCell = numCell; }
}