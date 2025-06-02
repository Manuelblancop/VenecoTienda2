package singleton;
import java.util.LinkedList;
import clases.Pedido;

public class SistemaDePedidos {
    private static SistemaDePedidos instancia;
    private LinkedList<Pedido> pedidos;

    private SistemaDePedidos() {
        pedidos = new LinkedList<>();
    }

    public static SistemaDePedidos getInstancia() {
        if (instancia == null) {
            instancia = new SistemaDePedidos();
        }
        return instancia;
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public LinkedList<Pedido> getPedidos() {
        return pedidos;
    }
}

