package BLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import DLL.ControllerDelivery;
import singleton.Conexion;

public class Delivery {
    private String[] sucursal = {"Sucursales"};
    private String direccion = "";
    private int idRepartidor;

    public Delivery(String[] sucursal, String direccion, int idRepartidor) {
        this.sucursal = sucursal;
        this.direccion = direccion;
        this.idRepartidor = idRepartidor;
    }

    public List<Productos> verProductos() {
        return ControllerDelivery.verProductos();
    }

    public void aceptarPedido() {
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido a aceptar:");
        try {
            int idPedido = Integer.parseInt(inputId);
            String resultado = ControllerDelivery.aceptarPedido(idPedido, idRepartidor);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void rechazarPedido() {
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido a rechazar:");
        try {
            int idPedido = Integer.parseInt(inputId);
            String resultado = ControllerDelivery.rechazarPedido(idPedido);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void verDetallesPedido() {
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido:");
        try {
            int idPedido = Integer.parseInt(inputId);
            String resultado = ControllerDelivery.verDetallesPedido(idPedido);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void marcarEntregado() {
        String inputId = JOptionPane.showInputDialog(null, "Ingrese el ID del pedido a marcar como entregado:");
        try {
            int idPedido = Integer.parseInt(inputId);
            String resultado = ControllerDelivery.marcarEntregado(idPedido);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
        }
    }

    public void verMenu() {
        String[] opciones = {"Ver Productos", "Aceptar Pedido", "Rechazar Pedido", "Ver Detalles de Pedido", "Marcar como Entregado", "Cerrar Sesión"};
        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(null, "Menú de Delivery", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            switch (seleccion) {
                case 0:
                    verProductos();
                    break;
                case 1:
                    aceptarPedido();
                    break;
                case 2:
                    rechazarPedido();
                    break;
                case 3:
                    verDetallesPedido();
                    break;
                case 4:
                    marcarEntregado();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
                    break;
                default:
                    if (seleccion != JOptionPane.CLOSED_OPTION) {
                        JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, seleccione una opción.");
                    }
                    break;
            }
        } while (seleccion != 5 && seleccion != JOptionPane.CLOSED_OPTION);
    }

    public String[] getSucursal() { return sucursal; }
    public void setSucursal(String[] sucursal) { this.sucursal = sucursal; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public int getIdRepartidor() { return idRepartidor; }
    public void setIdRepartidor(int idRepartidor) { this.idRepartidor = idRepartidor; }
}