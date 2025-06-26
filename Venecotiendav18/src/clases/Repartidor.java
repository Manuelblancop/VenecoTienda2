// Archivo: Repartidor.java
package clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import singleton.Conexion;

public class Repartidor extends Usuario {
    private int ID = 0;
    private int numCell = 0;

    public Repartidor(String nombre, String pass, String rol, int iD, int numCell) {
        super(nombre, pass, rol);
        ID = iD;
        this.numCell = numCell;
    }

    public List<Pedido> verPedidosAsignados() {
        List<Pedido> lista = new ArrayList<>();
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_pedido, productos, fk_cliente FROM pedido WHERE fk_estado_pedido = 2"; // Estado 2: Preparación
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getString("productos"),
                    rs.getInt("fk_cliente")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar pedidos: " + e.getMessage());
        }
        return lista;
    }

    public List<Pedido> verHistorialPedidos() {
        List<Pedido> lista = new ArrayList<>();
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_pedido, productos, fk_cliente FROM pedido";
            PreparedStatement stmt = conexion.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getString("productos"),
                    rs.getInt("fk_cliente")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar historial: " + e.getMessage());
        }
        return lista;
    }

    public boolean finalizarPedido(int string) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "UPDATE pedido SET fk_estado_pedido = 3 WHERE id_pedido = ?"; // Estado 3: Entregado
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, string);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al finalizar el pedido: " + e.getMessage());
            return false;
        }
    }

    public void verMapa() {
        JOptionPane.showMessageDialog(null, "Mapa mostrado para " + getNombre());
    }

    public String editarPerfil(String nuevoNombre, String nuevaPass) {
        if (nuevoNombre == null || nuevaPass == null || nuevoNombre.isEmpty() || nuevaPass.isEmpty()) {
            return "Nombre y contraseña no pueden estar vacíos.";
        }
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
            return "Perfil actualizado con éxito.";
        } catch (SQLException e) {
            return "Error al actualizar perfil: " + e.getMessage();
        }
    }

    public int getID() { return ID; }
    public void setID(int iD) { ID = iD; }
    public int getNumCell() { return numCell; }
    public void setNumCell(int numCell) { this.numCell = numCell; }
}
