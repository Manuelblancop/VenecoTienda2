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
        Connection conexion = null;
        PreparedStatement stmtUsuario = null;
        PreparedStatement stmtRelacion = null;
        ResultSet rs = null;
        try {
            conexion = Conexion.getInstance().getConnection();
            // Validar entradas
            if (nombre == null || nombre.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Nombre de usuario y contraseña no pueden estar vacíos.");
            }

            // Insertar en tabla usuario
            String queryUsuario = "INSERT INTO usuario (nombre_usuario, password) VALUES (?, ?)";
            stmtUsuario = conexion.prepareStatement(queryUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, nombre);
            stmtUsuario.setString(2, password);
            int rowsAffected = stmtUsuario.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se pudo insertar en la tabla usuario.");
            }

            // Obtener el id_usuario generado
            rs = stmtUsuario.getGeneratedKeys();
            int idUsuario = 0;
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            } else {
                throw new SQLException("No se generó un ID para el usuario.");
            }

            // Insertar en la tabla correspondiente según el rol
            switch (rol.toLowerCase()) {
                case "admin":
                    String queryAdmin = "INSERT INTO admin (fk_usuario, nombre) VALUES (?, ?)";
                    stmtRelacion = conexion.prepareStatement(queryAdmin);
                    stmtRelacion.setInt(1, idUsuario);
                    stmtRelacion.setString(2, nombre);
                    stmtRelacion.executeUpdate();
                    break;
                case "cliente":
                    String direccion = JOptionPane.showInputDialog(null, "Ingrese su dirección:");
                    if (direccion == null || direccion.trim().isEmpty()) {
                        throw new IllegalArgumentException("La dirección no puede estar vacía.");
                    }
                    String numCelInput = JOptionPane.showInputDialog(null, "Ingrese su número de celular:");
                    int numCel;
                    try {
                        numCel = Integer.parseInt(numCelInput);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Número de celular inválido.");
                    }
                    String queryCliente = "INSERT INTO cliente (fk_usuario, nombre, direccion, num_cel) VALUES (?, ?, ?, ?)";
                    stmtRelacion = conexion.prepareStatement(queryCliente);
                    stmtRelacion.setInt(1, idUsuario);
                    stmtRelacion.setString(2, nombre);
                    stmtRelacion.setString(3, direccion);
                    stmtRelacion.setInt(4, numCel);
                    stmtRelacion.executeUpdate();
                    break;
                case "empleado":
                    String cargo = JOptionPane.showInputDialog(null, "Ingrese su cargo (Administrador/Chef/Repartidor):");
                    if (cargo == null || cargo.trim().isEmpty()) {
                        throw new IllegalArgumentException("El cargo no puede estar vacío.");
                    }
                    String queryEmpleado = "INSERT INTO empleado (fk_usuario, nombre, cargo) VALUES (?, ?, ?)";
                    stmtRelacion = conexion.prepareStatement(queryEmpleado);
                    stmtRelacion.setInt(1, idUsuario);
                    stmtRelacion.setString(2, nombre);
                    stmtRelacion.setString(3, cargo);
                    stmtRelacion.executeUpdate();
                    break;
                case "repartidor":
                    String numCelRepInput = JOptionPane.showInputDialog(null, "Ingrese su número de celular:");
                    int numCelRep;
                    try {
                        numCelRep = Integer.parseInt(numCelRepInput);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Número de celular inválido.");
                    }
                    String queryRepartidor = "INSERT INTO repartidor (fk_usuario, nombre, num_cel) VALUES (?, ?, ?)";
                    stmtRelacion = conexion.prepareStatement(queryRepartidor);
                    stmtRelacion.setInt(1, idUsuario);
                    stmtRelacion.setString(2, nombre);
                    stmtRelacion.setInt(3, numCelRep);
                    stmtRelacion.executeUpdate();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Rol no válido.");
                    return;
            }

            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito como " + rol);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage() + "\nCódigo de error: " + e.getErrorCode());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmtUsuario != null) stmtUsuario.close();
                if (stmtRelacion != null) stmtRelacion.close();
                // No cerrar la conexión, ya que Conexion es un singleton
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

	public static int getIdUsuario(String nombre, String pass) {
		// TODO Auto-generated method stub
		return 0;
	}
}