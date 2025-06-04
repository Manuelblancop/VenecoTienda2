package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import DLL.ControllerEmpleado;
import singleton.Conexion;

public class Empleado extends Usuario {
    private int ID = 0;
    private String[] cargo = {"Administrador", "Chef", "Repartidor"};

    public Empleado(String nombre, String pass, String rol, int iD, String[] cargo) {
        super(nombre, pass, rol);
        ID = iD;
        this.cargo = cargo;
    }

    public List<Productos> verProductos() {
        return ControllerEmpleado.verProductos();
    }

    public void verPedidosAsignados() {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_pedido, productos, fk_cliente FROM pedido WHERE fk_empleado = ?";
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

    public void prepararPedido() {
        if (!cargo[0].equals("Chef")) {
            JOptionPane.showMessageDialog(null, "Solo los chefs pueden preparar pedidos.");
            return;
        }
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido a preparar:");
        try {
            int idPedido = Integer.parseInt(inputId);
            String resultado = ControllerEmpleado.prepararPedido(idPedido);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void verMenu() {
        String[] opciones = {"Ver Productos", "Ver Pedidos Asignados", "Preparar Pedido (si es Chef)", "Cerrar Sesión"};
        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(null, "Menú del Empleado", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (seleccion) {
                case 0:
                    verProductos();
                    break;
                case 1:
                    verPedidosAsignados();
                    break;
                case 2:
                    prepararPedido();
                    break;
                case 3:
                    cerrarSesion();
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

    public int getID() { return ID; }
    public void setID(int iD) { ID = iD; }
    public String[] getCargo() { return cargo; }
    public void setCargo(String[] cargo) { this.cargo = cargo; }
}