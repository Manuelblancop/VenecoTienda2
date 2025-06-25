package DLL;
import clases.*;
import singleton.Conexion;
import singleton.Sesion;
import javax.swing.*;
import java.sql.*;

public class AuthController {

    public static boolean login(String usuario, String password) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT * FROM usuario WHERE nombre_usuario = ? AND password = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String rol = verificarRol(conexion, idUsuario);
                Usuario user;

                switch (rol) {
                    case "admin":
                        user = new Admin(usuario, password, rol, idUsuario); break;
                    case "cliente":
                        user = new Cliente(usuario, password, rol, idUsuario, 0, ""); break;
                    case "repartidor":
                        user = new Repartidor(usuario, password, rol, idUsuario, 0); break;
                    default:
                        user = new Usuario(usuario, password, rol);
                }

                Sesion.getInstancia().iniciarSesion(user);
                return true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en login: " + e.getMessage());
        }
        return false;
    }

    public static boolean registrar(String nombre, String password, String rol) {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String queryUsuario = "INSERT INTO usuario (nombre_usuario, password) VALUES (?, ?)";
            PreparedStatement stmtUsuario = conexion.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS);
            stmtUsuario.setString(1, nombre);
            stmtUsuario.setString(2, password);
            stmtUsuario.executeUpdate();

            ResultSet rs = stmtUsuario.getGeneratedKeys();
            int idUsuario = rs.next() ? rs.getInt(1) : 0;

            String queryRelacion = "INSERT INTO " + rol + " (nombre, fk_usuario) VALUES (?, ?)";
            PreparedStatement stmtRol = conexion.prepareStatement(queryRelacion);
            stmtRol.setString(1, nombre);
            stmtRol.setInt(2, idUsuario);
            stmtRol.executeUpdate();

            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en registro: " + e.getMessage());
        }
        return false;
    }

    public static String verificarRol(Connection conexion, int idUsuario) throws SQLException {
        if (existeEnTabla(conexion, "admin", "fk_usuario", idUsuario)) return "admin";
        if (existeEnTabla(conexion, "admin", "fk_usuario", idUsuario)) return "admin";
        if (existeEnTabla(conexion, "admin", "fk_usuario", idUsuario)) return "admin";
        if (existeEnTabla(conexion, "empleado", "fk_usuario", idUsuario)) return "empleado";
        if (existeEnTabla(conexion, "repartidor", "fk_usuario", idUsuario)) return "repartidor";
        if (existeEnTabla(conexion, "cliente", "fk_usuario", idUsuario)) return "cliente";
        return "desconocido";
    }

    private static boolean existeEnTabla(Connection conexion, String tabla, String columna, int idUsuario) throws SQLException {
        String query = "SELECT 1 FROM " + tabla + " WHERE " + columna + " = ?";
        PreparedStatement stmt = conexion.prepareStatement(query);
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
}
