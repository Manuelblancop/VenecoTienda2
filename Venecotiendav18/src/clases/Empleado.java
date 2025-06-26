package clases;

import singleton.Menu;
import clases.Productos;

public class Empleado extends Usuario {
    private int id;
    private String cargo;

    public Empleado(String nombre, String pass, String rol, int id, String cargo) {
        super(nombre, pass, rol);
        this.id = id;
        this.cargo = cargo;
    }

    public int getIdEmpleado() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }

    public void verPedidos() {
        System.out.println("📋 Mostrando todos los pedidos actuales del sistema...");
        // Aquí deberías conectar con la base de datos y traer los pedidos
    }

    public void aceptarPedido(int idPedido) {
        System.out.println("✅ Pedido con ID " + idPedido + " aceptado.");
        // Aquí deberías actualizar el estado del pedido a "Aceptado" en la base de datos
    }

    public void agregarProductoAlMenu(String nombre, double precio) {
        Productos producto = new Productos(id, nombre, nombre, precio, null);
        Menu.getInstancia().agregarProducto(producto);
        System.out.println("🍽️ Producto agregado al menú: " + nombre + " - $" + precio);
    }

    public void verEstadosDePedidos() {
        System.out.println("🔎 Mostrando estado actual de los pedidos...");
        // Conexión a DB para mostrar estados
    }

    public void verHistorialDePedidos() {
        System.out.println("📜 Mostrando historial de pedidos completados...");
        // Conexión a DB para mostrar historial
    }
}
