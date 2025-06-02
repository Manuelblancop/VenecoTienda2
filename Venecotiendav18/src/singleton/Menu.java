package singleton;
import java.util.LinkedList;
import clases.Productos; 

public class Menu {
    private static Menu instancia;
    private LinkedList<Productos> productos;

    private Menu() {
        productos = new LinkedList<>();
    }

    public static Menu getInstancia() {
        if (instancia == null) {
            instancia = new Menu();
        }
        return instancia;
    }

    public void agregarProducto(Productos producto) {
        productos.add(producto);
    }

    public LinkedList<Productos> getProductos() {
        return productos;
    }
}

