// Archivo: AdminIterface.java
package GUI;

import clases.Admin;
import clases.Productos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AdminIterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panelContenido;
    private JTextArea areaResultados;
    private JPanel panelEditarProductos;
    private CardLayout cardLayout;

    public AdminIterface(Admin admin) {
        setTitle("Panel de Administrador");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Panel izquierdo con botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6, 1, 10, 10));
        panelBotones.setBackground(new Color(25, 39, 70));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(panelBotones, BorderLayout.WEST);

        JButton btnVerPedidos = new JButton("Ver Pedidos Asignados");
        JButton btnVerProductos = new JButton("Ver Productos");
        JButton btnVerEstadoPedidos = new JButton("Ver Estado de Pedidos");
        JButton btnEditarProductos = new JButton("Editar Productos");
        JButton btnEditarPerfil = new JButton("Editar Perfil");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        JButton[] botones = {btnVerPedidos, btnVerProductos, btnVerEstadoPedidos, btnEditarProductos, btnEditarPerfil, btnCerrarSesion};
        for (JButton btn : botones) {
            btn.setFocusPainted(false);
            btn.setBackground(new Color(255, 195, 0));
            btn.setForeground(Color.BLACK);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            panelBotones.add(btn);
        }

        // Panel de contenido con CardLayout
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        getContentPane().add(panelContenido, BorderLayout.CENTER);

        // Panel de resultados con JTextArea
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setBackground(new Color(9, 22, 42));
        areaResultados.setForeground(Color.WHITE);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollArea = new JScrollPane(areaResultados);
        panelContenido.add(scrollArea, "resultados");

        // Panel de productos editables
        panelEditarProductos = new JPanel();
        panelEditarProductos.setLayout(new BoxLayout(panelEditarProductos, BoxLayout.Y_AXIS));
        panelEditarProductos.setBackground(new Color(25, 39, 70));
        JScrollPane scrollEditar = new JScrollPane(panelEditarProductos);
        panelContenido.add(scrollEditar, "editarProductos");

        // Acción: Ver Pedidos Asignados
        btnVerPedidos.addActionListener(e -> {
            areaResultados.setText(admin.verPedidosAsignados());
            cardLayout.show(panelContenido, "resultados");
        });

        // Acción: Ver Productos
        btnVerProductos.addActionListener(e -> {
            areaResultados.setText(admin.verProductos());
            cardLayout.show(panelContenido, "resultados");
        });

        // Acción: Ver Estado de Pedidos
        btnVerEstadoPedidos.addActionListener(e -> {
            areaResultados.setText(admin.verEstadoPedidos());
            cardLayout.show(panelContenido, "resultados");
        });

        // Acción: Editar Productos
        btnEditarProductos.addActionListener(e -> {
            panelEditarProductos.removeAll();
            List<Productos> productos = admin.getProductos();
            for (Productos prod : productos) {
                JPanel prodPanel = new JPanel(new BorderLayout());
                prodPanel.setBackground(new Color(17, 34, 64));
                prodPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JLabel lbl = new JLabel("ID: " + prod.getID() + " | " + prod.getNombre() + " | $" + prod.getPrecio());
                lbl.setBackground(new Color(25, 39, 70));
                lbl.setForeground(Color.WHITE);

                JButton btnEditar = new JButton("Editar");
                btnEditar.setBackground(Color.YELLOW);

                btnEditar.addActionListener(ev -> {
                    String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", prod.getNombre());
                    if (nuevoNombre == null) return;
                    String nuevaDesc = JOptionPane.showInputDialog("Nueva descripción:", prod.getDescripcion());
                    if (nuevaDesc == null) return;
                    String precioStr = JOptionPane.showInputDialog("Nuevo precio:", prod.getPrecio());
                    if (precioStr == null) return;
                    try {
                        double nuevoPrecio = Double.parseDouble(precioStr);
                        String msg = admin.editarProducto(prod.getID(), nuevoNombre, nuevaDesc, nuevoPrecio);
                        JOptionPane.showMessageDialog(null, msg);
                        btnEditarProductos.doClick(); // Recargar lista
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Precio inválido.");
                    }
                });

                prodPanel.add(lbl, BorderLayout.CENTER);
                prodPanel.add(btnEditar, BorderLayout.EAST);
                panelEditarProductos.add(prodPanel);
            }
            panelEditarProductos.revalidate();
            panelEditarProductos.repaint();
            cardLayout.show(panelContenido, "editarProductos");
        });

        // Acción: Editar Perfil
        btnEditarPerfil.addActionListener(e -> {
            String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre de usuario:");
            String nuevaPass = JOptionPane.showInputDialog("Nueva contraseña:");
            String msg = admin.editarPerfil(nuevoNombre, nuevaPass);
            JOptionPane.showMessageDialog(null, msg);
        });

        // Acción: Cerrar Sesión
        btnCerrarSesion.addActionListener(e -> {
            admin.cerrarSesion();
            JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
            dispose();
        });

        // Mostrar vista por defecto
        cardLayout.show(panelContenido, "resultados");
    }
}
