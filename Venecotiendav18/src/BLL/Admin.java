package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import DLL.ControllerAdmin;
import singleton.Conexion;

public class Admin extends Usuario {
    private int ID = 0;

    public Admin(String nombre, String pass, String rol, int iD) {
        super(nombre, pass, rol);
        ID = iD;
    }

    public List<Productos> verProductos() {
        return ControllerAdmin.verProductos();
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

    public void verEstadoPedidos() {
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido:");
        try {
            int idPedido = Integer.parseInt(inputId);
            String resultado = ControllerAdmin.verEstadoPedidos(idPedido);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void eliminarProducto() {
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del producto a eliminar:");
        try {
            int idProducto = Integer.parseInt(inputId);
            String resultado = ControllerAdmin.eliminarProducto(idProducto);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void editarPerfil() {
        String nuevoNombre = JOptionPane.showInputDialog(null, "Nuevo nombre:");
        String nuevaPass = JOptionPane.showInputDialog(null, "Nueva contraseña:");
        if (nuevoNombre != null && nuevaPass != null && !nuevoNombre.isEmpty() && !nuevaPass.isEmpty()) {
            String resultado = ControllerAdmin.editarPerfil(getIdUsuario(), nuevoNombre, nuevaPass);
            if (resultado.equals("Perfil actualizado.")) {
                setNombre(nuevoNombre);
                setPass(nuevaPass);
            }
            JOptionPane.showMessageDialog(null, resultado);
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