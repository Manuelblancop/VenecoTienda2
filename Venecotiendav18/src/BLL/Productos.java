package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import singleton.Conexion;

public class Productos {
    private int ID = 0;
    private String nombre = "";
    private String descripcion = "";
    private Double precio = 0.0;
    private String[] categoria = {"Comida"};

    public Productos() {}

    public Productos(int iD, String nombre, String descripcion, Double precio, String[] categoria) {
        this.ID = iD;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    public int getID() { return ID; }
    public void setID(int iD) { ID = iD; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public String[] getCategoria() { return categoria; }
    public void setCategoria(String[] categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return "ID: " + ID + ", Nombre: " + nombre + ", Descripción: " + descripcion + ", Precio: $" + precio;
    }
}