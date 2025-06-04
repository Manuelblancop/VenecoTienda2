package clases;

import java.util.LinkedList;

public class Pedido {
    private LinkedList<Productos> listaProductos;
    private String metodoDePago;
    private double montoPagar;
    private int idPedido;
    private String estado;

    public Pedido() {
        listaProductos = new LinkedList<>();
        montoPagar = 0.0;
        estado = "Pendiente";
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
}