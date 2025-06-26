// Archivo: RepartidorInterface.java
package GUI;

import clases.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RepartidorInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panelContenido;
    private JTextArea areaResultados;
    private JPanel panelPedidosAsignados;
    private CardLayout cardLayout;

    public RepartidorInterface(Repartidor repartidor) {
        setTitle("Panel de Repartidor");
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

        JButton btnVerPedidos = new JButton("Seleccionar Pedido");
        JButton btnPedidosAsignados = new JButton("Ver Pedidos Asignados");
        JButton btnVerEstado = new JButton("Ver Estado del Pedido");
        JButton btnMapa = new JButton("Ver Mapa");
        JButton btnEditarPerfil = new JButton("Editar Perfil");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        JButton[] botones = {btnVerPedidos, btnPedidosAsignados, btnVerEstado, btnMapa, btnEditarPerfil, btnCerrarSesion};
        for (JButton btn : botones) {
            btn.setFocusPainted(false);
            btn.setBackground(new Color(255, 195, 0));
            btn.setForeground(Color.BLACK);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            panelBotones.add(btn);
        }

        // Panel derecho con CardLayout
        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        getContentPane().add(panelContenido, BorderLayout.CENTER);

        // Panel de resultados
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setBackground(new Color(9, 22, 42));
        areaResultados.setForeground(Color.WHITE);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollArea = new JScrollPane(areaResultados);
        panelContenido.add(scrollArea, "resultados");

        // Panel de pedidos asignados
        panelPedidosAsignados = new JPanel();
        panelPedidosAsignados.setLayout(new BoxLayout(panelPedidosAsignados, BoxLayout.Y_AXIS));
        panelPedidosAsignados.setBackground(new Color(25, 39, 70));
        JScrollPane scrollPedidos = new JScrollPane(panelPedidosAsignados);
        panelContenido.add(scrollPedidos, "pedidosAsignados");

        // Acción: Seleccionar Pedido
        btnVerPedidos.addActionListener(e -> {
            panelPedidosAsignados.removeAll();
            try {
                Connection conn = singleton.Conexion.getInstance().getConnection();
                String query = "SELECT id_pedido, productos FROM pedido WHERE fk_estado_pedido = 1"; // Preparación
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                boolean hayPedidos = false;
                while (rs.next()) {
                    hayPedidos = true;
                    int id = rs.getInt("id_pedido");
                    String productos = rs.getString("productos");

                    JPanel pedidoPanel = new JPanel(new BorderLayout());
                    pedidoPanel.setBackground(new Color(17, 34, 64));
                    pedidoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    JLabel lbl = new JLabel("ID: " + id + " | Productos: " + productos);
                    lbl.setForeground(Color.WHITE);

                    JButton btnAsignar = new JButton("Asignar a mí");
                    btnAsignar.setBackground(Color.YELLOW);
                    btnAsignar.addActionListener(ev -> {
                        try {
                            String update = "UPDATE pedido SET fk_estado_pedido = 2 WHERE id_pedido = ?"; // Asignado
                            PreparedStatement ps = conn.prepareStatement(update);
                            ps.setInt(1, id);
                            ps.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Pedido asignado con éxito.");
                            btnVerPedidos.doClick();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error al asignar pedido.");
                        }
                    });

                    pedidoPanel.add(lbl, BorderLayout.CENTER);
                    pedidoPanel.add(btnAsignar, BorderLayout.EAST);
                    panelPedidosAsignados.add(pedidoPanel);
                }
                if (!hayPedidos) {
                    JLabel label = new JLabel("No hay pedidos en preparación.");
                    label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
                    label.setForeground(new Color(255, 255, 255));
                    panelPedidosAsignados.add(label);
                }
            } catch (Exception ex) {
                JLabel label = new JLabel("Error cargando pedidos: " + ex.getMessage());
                label.setForeground(new Color(255, 255, 255));
                label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
                panelPedidosAsignados.add(label);
            }
            panelPedidosAsignados.revalidate();
            panelPedidosAsignados.repaint();
            cardLayout.show(panelContenido, "pedidosAsignados");
        });

        // Acción: Ver Pedidos Asignados
        btnPedidosAsignados.addActionListener(e -> {
            areaResultados.setText("");
            try {
                Connection conn = singleton.Conexion.getInstance().getConnection();
                String query = "SELECT id_pedido, productos FROM pedido WHERE fk_estado_pedido = 2";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                StringBuilder sb = new StringBuilder("Pedidos Asignados:\n");
                while (rs.next()) {
                    sb.append("ID: ").append(rs.getInt("id_pedido"))
                      .append(" | Productos: ").append(rs.getString("productos"))
                      .append("\n");
                }
                areaResultados.setText(sb.toString());
            } catch (Exception ex) {
                areaResultados.setText("Error: " + ex.getMessage());
            }
            cardLayout.show(panelContenido, "resultados");
        });

        // Acción: Ver Estado del Pedido y finalizar
        btnVerEstado.addActionListener(e -> {
            panelPedidosAsignados.removeAll();
            try {
                Connection conn = singleton.Conexion.getInstance().getConnection();
                String query = "SELECT id_pedido, productos FROM pedido WHERE fk_estado_pedido = 2";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                boolean hayPedidos = false;
                while (rs.next()) {
                    hayPedidos = true;
                    int id = rs.getInt("id_pedido");
                    String productos = rs.getString("productos");

                    JPanel pedidoPanel = new JPanel(new BorderLayout());
                    pedidoPanel.setBackground(new Color(17, 34, 64));
                    pedidoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    JLabel lbl = new JLabel("ID: " + id + " | Productos: " + productos);
                    lbl.setForeground(Color.WHITE);

                    JButton btnFinalizar = new JButton("Finalizar");
                    btnFinalizar.setBackground(Color.GREEN);
                    btnFinalizar.addActionListener(ev -> {
                        boolean ok = repartidor.finalizarPedido(id);
                        if (ok) {
                            JOptionPane.showMessageDialog(null, "Pedido finalizado.");
                            btnVerEstado.doClick();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al finalizar el pedido.");
                        }
                    });

                    pedidoPanel.add(lbl, BorderLayout.CENTER);
                    pedidoPanel.add(btnFinalizar, BorderLayout.EAST);
                    panelPedidosAsignados.add(pedidoPanel);
                }
                if (!hayPedidos) {
                    JLabel label = new JLabel("No tienes pedidos en curso.");
                    label.setForeground(new Color(255, 255, 255));
                    label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
                    panelPedidosAsignados.add(label);
                }
            } catch (Exception ex) {
                panelPedidosAsignados.add(new JLabel("Error: " + ex.getMessage()));
            }
            panelPedidosAsignados.revalidate();
            panelPedidosAsignados.repaint();
            cardLayout.show(panelContenido, "pedidosAsignados");
        });

        // Acción: Ver Mapa
        btnMapa.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Mostrando el mapa (vista simulada)");
        });

        // Acción: Editar Perfil
        btnEditarPerfil.addActionListener(e -> {
            repartidor.editarPerfil(getTitle(), getTitle());
        });

        // Acción: Cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            repartidor.cerrarSesion();
            JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
            dispose();
        });

        cardLayout.show(panelContenido, "resultados");
    }
}


