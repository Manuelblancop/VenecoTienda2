package GUI;
import clases.Admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminIt extends JFrame {

    private Admin admin;
    private JTextArea areaResultados;
    private JButton btnVerPedidos, btnVerProductos, btnVerEstadoPedidos, btnEliminarProducto, btnEditarPerfil, btnCerrarSesion;

    public AdminIt(Admin admin) {
        this.admin = admin;

        ///////////////////////////////Configuración básica de la ventana JFrame
        setTitle("Panel de Admin");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ////////////////////////////Crear el área de resultados donde se mostrará la información
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        add(scrollPane, BorderLayout.CENTER);

        ////////////////////////////Crear los botones para las acciones del admin
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6, 1));  // 6 botones en columna
        add(panelBotones, BorderLayout.WEST);

        btnVerPedidos = new JButton("Ver Pedidos Asignados");
        btnVerProductos = new JButton("Ver Productos");
        btnVerEstadoPedidos = new JButton("Ver Estado de Pedidos");
        btnEliminarProducto = new JButton("Eliminar Producto");
        btnEditarPerfil = new JButton("Editar Perfil");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        panelBotones.add(btnVerPedidos);
        panelBotones.add(btnVerProductos);
        panelBotones.add(btnVerEstadoPedidos);
        panelBotones.add(btnEliminarProducto);
        panelBotones.add(btnEditarPerfil);
        panelBotones.add(btnCerrarSesion);

        ///////////////////////////////////////Agregar los eventos de los botones
        btnVerPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.verPedidosAsignados();
            }
        });

        btnVerProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.verProductos();
            }
        });

        btnVerEstadoPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.verEstadoPedidos();
            }
        });

        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.eliminarProducto();
            }
        });

        btnEditarPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.editarPerfil();
            }
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.cerrarSesion();
                JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
                dispose();  // Cerrar el JFrame de Admin
            }
        });
    }

    public static void main(String[] args) {
        // Crear un objeto Admin y abrir el JFrame
        Admin admin = new Admin("Admin", "password", "Administrador", 1);
        AdminIt frame = new AdminIt(admin);
        frame.setVisible(true);
    }
}


