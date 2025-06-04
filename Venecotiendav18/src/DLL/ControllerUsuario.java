package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import BLL.Usuario;
import singleton.Conexion;

public class ControllerUsuario {
    public static boolean iniciarSesion(Usuario usuario) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_usuario FROM usuario WHERE nombre_usuario = ? AND password = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getPass());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso. Bienvenido, " + usuario.getNombre());
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
            return false;
        }
    }

    public static void registrarUsuario(String nombre, String password, String rol) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            // Insertar en tabla usuario
            String queryUsuario = "INSERT INTO usuario (nombre_usuario, password) VALUES (?, ?)";
            PreparedStatement stmtUsuario = conexion.prepareStatement(queryUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, nombre);
            stmtUsuario.setString(2, password);
            stmtUsuario.executeUpdate();

            ResultSet rs = stmtUsuario.getGeneratedKeys();
            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }

            // Insertar en tabla correspondiente según rol
            String tablaRelacionada = "";
            PreparedStatement stmtRelacion = null;
            switch (rol.toLowerCase()) {
                case "admin":
                    tablaRelacionada = "admin";
                    String queryAdmin = "INSERT INTO admin (nombre, fk_usuario) VALUES (?, ?)";
                    stmtRelacion = conexion.prepareStatement(queryAdmin);
                    stmtRelacion.setString(1, nombre);
                    stmtRelacion.setInt(2, idUsuario);
                    break;
                case "cliente":
                    String direccion = JOptionPane.showInputDialog(null, "Ingrese su dirección:");
                    String numCelInput = JOptionPane.showInputDialog(null, "Ingrese su número de celular:");
                    int numCel = Integer.parseInt(numCelInput);
                    tablaRelacionada = "cliente";
                    String queryCliente = "INSERT INTO cliente (nombre, direccion, num_cel, fk_usuario) VALUES (?, ?, ?, ?)";
                    stmtRelacion = conexion.prepareStatement(queryCliente);
                    stmtRelacion.setString(1, nombre);
                    stmtRelacion.setString(2, direccion);
                    stmtRelacion.setInt(3, numCel);
                    stmtRelacion.setInt(4, idUsuario);
                    break;
                case "empleado":
                    String cargo = JOptionPane.showInputDialog(null, "Ingrese su cargo (Administrador/Chef/Repartidor):");
                    tablaRelacionada = "empleado";
                    String queryEmpleado = "INSERT INTO empleado (nombre, cargo, fk_usuario) VALUES (?, ?, ?)";
                    stmtRelacion = conexion.prepareStatement(queryEmpleado);
                    stmtRelacion.setString(1, nombre);
                    stmtRelacion.setString(2, cargo);
                    stmtRelacion.setInt(3, idUsuario);
                    break;
                case "repartidor":
                    String numCelRepInput = JOptionPane.showInputDialog(null, "Ingrese su número de celular:");
                    int numCelRep = Integer.parseInt(numCelRepInput);
                    tablaRelacionada = "repartidor";
                    String queryRepartidor = "INSERT INTO repartidor (nombre, num_cel, fk_usuario) VALUES (?, ?, ?)";
                    stmtRelacion = conexion.prepareStatement(queryRepartidor);
                    stmtRelacion.setString(1, nombre);
                    stmtRelacion.setInt(2, numCelRep);
                    stmtRelacion.setInt(3, idUsuario);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Rol no válido.");
                    return;
            }

            if (!tablaRelacionada.isEmpty()) {
                stmtRelacion.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario registrado con éxito como " + rol);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de celular inválido.");
        }
    }

	public static int getIdUsuario(String nombre, String pass) {
		// TODO Auto-generated method stub
		return 0;
	}
}