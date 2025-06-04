package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import DLL.ControllerPedido;
import singleton.Conexion;

public class Pedido {
    private LinkedList<Productos> listaProductos;
    private String metodoDePago;
    private double montoPagar;
    private int idPedido;
    private String estado;
    private int idCliente;
    private int idRepartidor;
    private int idEmpleado;

    public Pedido() {
        listaProductos = new LinkedList<>();
        montoPagar = 0.0;
        estado = "Pendiente";
    }

    public void registrarPedido(int idCliente) {
        List<Productos> productosDisponibles = ControllerPedido.verProductos();
        if (productosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos disponibles para hacer un pedido.");
            return;
        }

        List<Productos> productosSeleccionados = new ArrayList<>();
        double total = 0.0;
        StringBuilder resumenProductos = new StringBuilder();
        boolean continuarSeleccionando = true;

        while (continuarSeleccionando) {
            String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del producto a agregar (o '0' para terminar):");
            try {
                int idProducto = Integer.parseInt(inputId);
                if (idProducto == 0) {
                    continuarSeleccionando = false;
                    continue;
                }

                Productos productoSeleccionado = null;
                for (Productos p : productosDisponibles) {
                    if (p.getID() == idProducto) {
                        productoSeleccionado = p;
                        break;
                    }
                }

                if (productoSeleccionado != null) {
                    productosSeleccionados.add(productoSeleccionado);
                    total += productoSeleccionado.getPrecio();
                    resumenProductos.append(productoSeleccionado.getNombre()).append(", ");
                } else {
                    JOptionPane.showMessageDialog(null, "Producto con ID " + idProducto + " no encontrado.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
            }
        }

        if (productosSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se seleccionaron productos. Pedido cancelado.");
            return;
        }

        String productosString = resumenProductos.length() > 2 ?
                                resumenProductos.substring(0, resumenProductos.length() - 2) :
                                resumenProductos.toString();
        this.listaProductos.addAll(productosSeleccionados);
        this.montoPagar = total;
        this.idCliente = idCliente;
        ControllerPedido.registrarPedido(this, idCliente, productosString, total);
    }

    public void verEstado() {
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido:");
        try {
            int idPedido = Integer.parseInt(inputId);
            String resultado = ControllerPedido.verEstadoPedido(idPedido);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public LinkedList<Productos> getListaProductos() { return listaProductos; }
    public void setListaProductos(LinkedList<Productos> listaProductos) { this.listaProductos = listaProductos; }
    public String getMetodoDePago() { return metodoDePago; }
    public void setMetodoDePago(String metodoDePago) { this.metodoDePago = metodoDePago; }
    public double getMontoPagar() { return montoPagar; }
    public void setMontoPagar(double montoPagar) { this.montoPagar = montoPagar; }
    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public int getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(int idRepartidor) { this.idRepartidor = idRepartidor; }
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
}