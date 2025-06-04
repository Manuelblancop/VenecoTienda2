package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import DLL.ControllerUsuario;

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
        boolean exito = ControllerUsuario.iniciarSesion(this);
        if (exito) {
            this.idUsuario = ControllerUsuario.getIdUsuario(this.nombre, this.pass);
        }
        return exito;
    }

    public void cerrarSesion() {
        JOptionPane.showMessageDialog(null, "Sesión cerrada.");
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}