package venecotienda;

import javax.swing.JOptionPane;

import DLL.ControllerCliente;
import DLL.ControllerProducto;
import clases.Admin;
import clases.Cliente;
import clases.Repartidor;
import clases.Usuario;
import enums.MenuAdmin;
import enums.MenuCliente;
import enums.MenuEmpleado;
import enums.MenuRepartidor;
import singleton.Conexion;
import singleton.Sesion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
    	ControllerCliente.verHistorialPedidos(1);
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            if (conexion == null) {
                JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos.");
                return;
            }

            String[] opcionesInicio = {"Iniciar Sesión", "Registrarse"};
            int opcionInicio = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Venecotienda",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcionesInicio, opcionesInicio[0]);

            if (opcionInicio == 0) {
                String usuarioInput = JOptionPane.showInputDialog(null, "Usuario:");
                String passwordInput = JOptionPane.showInputDialog(null, "Contraseña:");
                if (usuarioInput != null && passwordInput != null) {
                    String query = "SELECT * FROM usuario WHERE nombre_usuario = ? AND password = ?";
                    PreparedStatement stmt = conexion.prepareStatement(query);
                    stmt.setString(1, usuarioInput);
                    stmt.setString(2, passwordInput);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        int idUsuario = rs.getInt("id_usuario");
                        String nombre = rs.getString("nombre_usuario");
                        String rol = verificarRol(conexion, idUsuario);
                        Usuario usuarioLogueado;
                        switch (rol) {
                            case "admin":
                                usuarioLogueado = new Admin(nombre, passwordInput, rol, idUsuario);
                                break;
                            case "cliente":
                                usuarioLogueado = new Cliente(nombre, passwordInput, rol, idUsuario, 123456789, "Calle Falsa 123");
                                break;
                            case "repartidor":
                                usuarioLogueado = new Repartidor(nombre, passwordInput, rol, idUsuario, 123456789);
                                break;
                            default:
                                usuarioLogueado = new Usuario(nombre, passwordInput, rol);
                        }
                        Sesion.getInstancia().iniciarSesion(usuarioLogueado);
                        JOptionPane.showMessageDialog(null, "Bienvenido, " + nombre);
                        mostrarMenu(usuarioLogueado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                    }
                }
            } else if (opcionInicio == 1) {
                String nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario:");
                String password = JOptionPane.showInputDialog(null, "Ingrese su contraseña:");
                String rol = JOptionPane.showInputDialog(null, "Ingrese su rol (admin/cliente/empleado/repartidor):");
                if (nombre != null && password != null && rol != null) {
                    registrarUsuario(conexion, nombre, password, rol);
                }
            }

            conexion.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al interactuar con la base de datos: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        }
    }

    private static void mostrarMenu(Usuario usuario) {
        switch (usuario.getRol()) {
            case "admin":
                ((Admin) usuario).verMenu();
                break;
            case "cliente":
                ((Cliente) usuario).verMenu();
                break;
            case "repartidor":
                ((Repartidor) usuario).verMenu();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Rol no identificado.");
        }
    }

    private static void registrarUsuario(Connection conexion, String nombre, String password, String rol) throws SQLException {
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

        String tablaRelacionada = "";
        switch (rol.toLowerCase()) {
            case "admin":
                tablaRelacionada = "admin";
                break;
            case "cliente":
                String direccion = JOptionPane.showInputDialog(null, "Ingrese su dirección:");
                int numCel = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese su número de celular:"));
                tablaRelacionada = "cliente";
                String queryCliente = "INSERT INTO cliente (nombre, direccion, num_cel, fk_usuario) VALUES (?, ?, ?, ?)";
                PreparedStatement stmtCliente = conexion.prepareStatement(queryCliente);
                stmtCliente.setString(1, nombre);
                stmtCliente.setString(2, direccion);
                stmtCliente.setInt(3, numCel);
                stmtCliente.setInt(4, idUsuario);
                stmtCliente.executeUpdate();
                break;
            case "empleado":
                tablaRelacionada = "empleado";
                break;
            case "repartidor":
                tablaRelacionada = "repartidor";
                break;
        }

        if (!tablaRelacionada.isEmpty()) {
            String queryRelacion = "INSERT INTO " + tablaRelacionada + " (nombre, fk_usuario) VALUES (?, ?)";
            PreparedStatement stmtRelacion = conexion.prepareStatement(queryRelacion);
            stmtRelacion.setString(1, nombre);
            stmtRelacion.setInt(2, idUsuario);
            stmtRelacion.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito como " + rol);
        }
    }

    public static String verificarRol(Connection conexion, int idUsuario) throws SQLException {
        if (existeEnTabla(conexion, "admin", "Usuario_id_usuario", idUsuario)) return "admin";
        if (existeEnTabla(conexion, "empleado", "fk_usuario", idUsuario)) return "empleado";
        if (existeEnTabla(conexion, "repartidor", "fk_usuario", idUsuario)) return "repartidor";
        if (existeEnTabla(conexion, "cliente", "fk_usuario", idUsuario)) return "cliente";
        return "desconocido";
    }

    public static boolean existeEnTabla(Connection conexion, String tabla, String columna, int idUsuario) throws SQLException {
        String query = "SELECT 1 FROM " + tabla + " WHERE " + columna + " = ?";
        PreparedStatement stmt = conexion.prepareStatement(query);
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
}
