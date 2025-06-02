package clases;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import singleton.Conexion;

public class Pedido {
    private LinkedList<Productos> listaProductos;
    private String metodoDePago;
    private double montoPagar;
    private int idPedido;

    public Pedido() {
        listaProductos = new LinkedList<>();
        montoPagar = 0.0;
    }

    public void agregarProducto(Productos producto) {
        listaProductos.add(producto);
        montoPagar += producto.getPrecio();
        JOptionPane.showMessageDialog(null, "Producto agregado. Total: " + montoPagar);
    }

    public void personalizarPedido(String instrucciones) {
        JOptionPane.showMessageDialog(null, "Pedido personalizado con: " + instrucciones);
    }

    public void generarRecomendaciones() {
        JOptionPane.showMessageDialog(null, "Recomendación: Agregar bebida.");
    }

    public void pagarPedido(String metodoDePago) {
        this.metodoDePago = metodoDePago;
        try {
            Connection conexion = Conexion.getInstance().getConnection();
            String query = "UPDATE pedido SET fk_met_pago = ? WHERE id_pedido = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setInt(1, obtenerIdMetodoPago(metodoDePago));
            stmt.setInt(2, idPedido);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pago realizado con " + metodoDePago);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al pagar.");
        }
    }

    private int obtenerIdMetodoPago(String metodo) {
        // Simulación, debería buscar en la tabla metodo_pago
        return 1; // Ejemplo
    }

    public LinkedList<Productos> getListaProductos() { return listaProductos; }
    public double getMontoPagar() { return montoPagar; }
    public String getMetodoDePago() { return metodoDePago; }
    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
}