package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import singleton.Conexion;

public class Usuario {
    private String nombre = "";
    private String pass = "";
    private String rol = "";
    private int idUsuario;

    public Usuario(String nombre, String pass, String rol) {
        this.nombre = nombre;
        this.pass = pass;
        this.rol = rol;
    }

    public boolean iniciarSesion() {
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "SELECT id_usuario FROM usuario WHERE nombre_usuario = ? AND password = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idUsuario = rs.getInt("id_usuario");
                JOptionPane.showMessageDialog(null, "Bienvenido, " + nombre);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
            return false;
        }
    }

    public void cerrarSesion() {
        singleton.Sesion.getInstancia().cerrarSesion();
        JOptionPane.showMessageDialog(null, "Sesión cerrada.");
    }

<<<<<<< Updated upstream
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
=======
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public int getIdUsuario() { return idUsuario; }
>>>>>>> Stashed changes
}