package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import DLL.ControllerRepartidor;
import singleton.Conexion;

public class Repartidor extends Usuario {
    private int ID = 0;
    private int numCel = 0;

    public Repartidor(String nombre, String pass, String rol, int iD, int numCel) {
        super(nombre, pass, rol);
        ID = iD;
        this.numCel = numCel;
    }

    public List<Productos> verProductos() {
        return ControllerRepartidor.verProductos();
    }

    public void verPedidosAsignados() {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_pedido, productos, fk_cliente FROM pedido WHERE fk_repartidor = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();
            StringBuilder sb = new StringBuilder("Pedidos Asignados:\n");
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
            String query = "SELECT id_pedido, productos FROM pedido WHERE fk_repartidor = ?";
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
                JOptionPane.showMessageDialog(null, "No hay historial.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar historial: " + e.getMessage());
        }
    }

    public void verEstadoPedido() {
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido:");
        try {
            int idPedido = Integer.parseInt(inputId);
            String resultado = ControllerRepartidor.verEstadoPedido(idPedido);
            JOptionPane.showMessageDialog(null, resultado);
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
        String inputNumCel = JOptionPane.showInputDialog(null, "Nuevo número de celular:");
        try {
            int nuevoNumCel = Integer.parseInt(inputNumCel);
            if (nuevoNombre != null && nuevaPass != null && !nuevoNombre.isEmpty() && !nuevaPass.isEmpty()) {
                String resultado = ControllerRepartidor.editarPerfil(getIdUsuario(), nuevoNombre, nuevaPass, nuevoNumCel);
                if (resultado.equals("Perfil actualizado.")) {
                    setNombre(nuevoNombre);
                    setPass(nuevaPass);
                    this.numCel = nuevoNumCel;
                }
                JOptionPane.showMessageDialog(null, resultado);
            } else {
                JOptionPane.showMessageDialog(null, "Nombre y contraseña no pueden estar vacíos.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número de celular válido.");
        }
    }

    public void verMenu() {
        String[] opciones = {"Ver Productos", "Ver Pedidos Asignados", "Ver Historial", "Ver Estado de Pedido", "Ver Mapa", "Editar Perfil", "Cerrar Sesión"};
        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(null, "Menú del Repartidor", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (seleccion) {
                case 0:
                    verProductos();
                    break;
                case 1:
                    verPedidosAsignados();
                    break;
                case 2:
                    verHistorialPedidos();
                    break;
                case 3:
                    verEstadoPedido();
                    break;
                case 4:
                    verMapa();
                    break;
                case 5:
                    editarPerfil();
                    break;
                case 6:
                    cerrarSesion();
                    JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
                    break;
                default:
                    if (seleccion != JOptionPane.CLOSED_OPTION) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, seleccione una opción.");
                    }
                    break;
            }
        } while (seleccion != 6 && seleccion != JOptionPane.CLOSED_OPTION);
    }

    public int getID() { return ID; }
    public void setID(int iD) { ID = iD; }
    public int getNumCel() { return numCel; }
    public void setNumCel(int numCel) { this.numCel = numCel; }
}