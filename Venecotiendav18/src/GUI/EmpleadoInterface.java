// Archivo: EmpleadoInterface.java
package GUI;

import clases.Empleado;
import clases.Productos;
import singleton.Menu;
import singleton.Conexion;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class EmpleadoInterface extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel panelContenido;
    private JTextArea areaResultados;
    private JPanel panelEditarMenu;
    private JPanel panelAcciones;
    private CardLayout cardLayout;

    public EmpleadoInterface(Empleado empleado) {
        setTitle("Panel de Empleado");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 10, 10));
        panelBotones.setBackground(new Color(25, 39, 70));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(panelBotones, BorderLayout.WEST);

        JButton btnVerPedidos = new JButton("Ver Pedidos");
        JButton btnVerMenu = new JButton("Ver Menú del Día");
        JButton btnEditarMenu = new JButton("Editar Menú del Día");
        JButton btnVerHistorial = new JButton("Ver Historial");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        JButton[] botones = {btnVerPedidos, btnVerMenu, btnEditarMenu, btnVerHistorial, btnCerrarSesion};
        for (JButton btn : botones) {
            btn.setFocusPainted(false);
            btn.setBackground(new Color(255, 195, 0));
            btn.setForeground(Color.BLACK);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            panelBotones.add(btn);
        }

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        getContentPane().add(panelContenido, BorderLayout.CENTER);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setBackground(new Color(9, 22, 42));
        areaResultados.setForeground(Color.WHITE);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollArea = new JScrollPane(areaResultados);

        // Panel que contiene el área de resultados y los botones de acción
        JPanel panelResultadosConAcciones = new JPanel(new BorderLayout());
        panelResultadosConAcciones.add(scrollArea, BorderLayout.CENTER);
        panelAcciones = new JPanel();
        panelAcciones.setLayout(new BoxLayout(panelAcciones, BoxLayout.Y_AXIS));
        panelAcciones.setBackground(new Color(25, 39, 70));
        panelResultadosConAcciones.add(panelAcciones, BorderLayout.EAST);

        panelContenido.add(panelResultadosConAcciones, "resultados");

        panelEditarMenu = new JPanel();
        panelEditarMenu.setLayout(new BoxLayout(panelEditarMenu, BoxLayout.Y_AXIS));
        panelEditarMenu.setBackground(new Color(25, 39, 70));
        JScrollPane scrollEditar = new JScrollPane(panelEditarMenu);
        panelContenido.add(scrollEditar, "editarMenu");

        //////// Ver pedidos
        btnVerPedidos.addActionListener(e -> {
            try {
                panelAcciones.removeAll();
                StringBuilder sb = new StringBuilder("📋 Pedidos Solicitados:\n\n");
                Connection conexion = Conexion.getInstance().getConnection();
                String query = "SELECT id_pedido, productos, fk_cliente FROM pedido WHERE fk_estado_pedido = 4";
                PreparedStatement stmt = conexion.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id_pedido");
                    String productos = rs.getString("productos");
                    int cliente = rs.getInt("fk_cliente");
                    sb.append("Pedido ID: ").append(id).append(" | Cliente ID: ").append(cliente)
                      .append("\nProductos: ").append(productos).append("\n\n-------------------\n");

                    JButton btnAceptar = new JButton("Aceptar Pedido " + id);
                    btnAceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
                    btnAceptar.setBackground(new Color(255, 195, 0));
                    btnAceptar.setFocusPainted(false);
                    btnAceptar.addActionListener(ev -> {
                        try {
                            String update = "UPDATE pedido SET fk_estado_pedido = 1 WHERE id_pedido = ?";
                            PreparedStatement stmt2 = conexion.prepareStatement(update);
                            stmt2.setInt(1, id);
                            stmt2.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Pedido " + id + " aceptado. Ahora está En preparación.");
                            btnVerPedidos.doClick();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error al aceptar pedido: " + ex.getMessage());
                        }
                    });
                    panelAcciones.add(Box.createRigidArea(new Dimension(0, 5)));
                    panelAcciones.add(btnAceptar);
                }

                panelAcciones.revalidate();
                panelAcciones.repaint();
                areaResultados.setText(sb.toString());
                cardLayout.show(panelContenido, "resultados");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar pedidos: " + ex.getMessage());
            }
        });
        ////////Ver Menú
        btnVerMenu.addActionListener(e -> {
            try {
                areaResultados.setText("🍽️ Menú del Día (Base de Datos):\n\n");
                Connection conexion = Conexion.getInstance().getConnection();
                String query = "SELECT * FROM producto";
                PreparedStatement stmt = conexion.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id_producto");
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    double precio = rs.getDouble("precio");

                    areaResultados.append("🆔 ID: " + id + "\n📦 " + nombre + " - $" + precio + "\n📄 " + descripcion + "\n\n");
                }

                panelAcciones.removeAll();
                cardLayout.show(panelContenido, "resultados");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar el menú: " + ex.getMessage());
            }
        });
        ////////Ver Editar el menú
        btnEditarMenu.addActionListener(e -> {
            panelEditarMenu.removeAll();
            try {
                Connection conexion = Conexion.getInstance().getConnection();
                String query = "SELECT * FROM producto";
                PreparedStatement stmt = conexion.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id_producto");
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    double precio = rs.getDouble("precio");

                    JPanel prodPanel = new JPanel(new BorderLayout());
                    prodPanel.setBackground(new Color(17, 34, 64));
                    prodPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                    JLabel lbl = new JLabel("ID: " + id + " | " + nombre + " | $" + precio);
                    lbl.setForeground(Color.WHITE);

                    JButton btnEditar = new JButton("Editar");
                    btnEditar.setBackground(Color.YELLOW);
                    btnEditar.addActionListener(ev -> {
                        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", nombre);
                        if (nuevoNombre == null) return;
                        String nuevaDesc = JOptionPane.showInputDialog("Nueva descripción:", descripcion);
                        if (nuevaDesc == null) return;
                        String precioStr = JOptionPane.showInputDialog("Nuevo precio:", precio);
                        if (precioStr == null) return;

                        try {
                            double nuevoPrecio = Double.parseDouble(precioStr);
                            String update = "UPDATE productos SET nombre=?, descripcion=?, precio=? WHERE id_producto=?";
                            PreparedStatement stmt2 = conexion.prepareStatement(update);
                            stmt2.setString(1, nuevoNombre);
                            stmt2.setString(2, nuevaDesc);
                            stmt2.setDouble(3, nuevoPrecio);
                            stmt2.setInt(4, id);
                            stmt2.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Producto actualizado.");
                            btnEditarMenu.doClick();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error al actualizar: " + ex.getMessage());
                        }
                    });

                    prodPanel.add(lbl, BorderLayout.CENTER);
                    prodPanel.add(btnEditar, BorderLayout.EAST);
                    panelEditarMenu.add(prodPanel);
                }

                // Botón para agregar nuevo producto
                JButton btnAgregar = new JButton("➕ Agregar Producto Nuevo");
                btnAgregar.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnAgregar.setBackground(new Color(255, 195, 0));
                btnAgregar.addActionListener(ev -> {
                    String nuevoNombre = JOptionPane.showInputDialog("Nombre:");
                    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) return;
                    String nuevaDesc = JOptionPane.showInputDialog("Descripción:");
                    if (nuevaDesc == null || nuevaDesc.trim().isEmpty()) return;
                    String precioStr = JOptionPane.showInputDialog("Precio:");
                    if (precioStr == null || precioStr.trim().isEmpty()) return;
                    try {
                        double nuevoPrecio = Double.parseDouble(precioStr);
                        String insert = "INSERT INTO productos (nombre, descripcion, precio) VALUES (?, ?, ?)";
                        PreparedStatement stmt3 = conexion.prepareStatement(insert);
                        stmt3.setString(1, nuevoNombre);
                        stmt3.setString(2, nuevaDesc);
                        stmt3.setDouble(3, nuevoPrecio);
                        stmt3.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Producto agregado.");
                        btnEditarMenu.doClick();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al agregar: " + ex.getMessage());
                    }
                });

                panelEditarMenu.add(Box.createRigidArea(new Dimension(0, 10)));
                panelEditarMenu.add(btnAgregar);

                panelEditarMenu.revalidate();
                panelEditarMenu.repaint();
                cardLayout.show(panelContenido, "editarMenu");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al editar menú: " + ex.getMessage());
            }
        });
        ////////Ver el historial
        btnVerHistorial.addActionListener(e -> {
            areaResultados.setText("📜 Historial de Pedidos:\n\n");
            panelAcciones.removeAll();

            try {
                Connection conexion = Conexion.getInstance().getConnection();
                String query = "SELECT p.id_pedido, p.productos, p.fk_cliente, ep.nombre " +
                               "FROM pedido p " +
                               "JOIN estado_pedido ep ON p.fk_estado_pedido = ep.id_estado";
                PreparedStatement stmt = conexion.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id_pedido");
                    String productos = rs.getString("productos");
                    int cliente = rs.getInt("fk_cliente");
                    String estado = rs.getString("nombre"); // ← columna correcta

                    areaResultados.append("🆔 Pedido ID: " + id +
                                          "\n👤 Cliente ID: " + cliente +
                                          "\n📦 Productos: " + productos +
                                          "\n📍 Estado: " + estado +
                                          "\n\n----------------------------\n\n");
                }

                cardLayout.show(panelContenido, "resultados");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar historial: " + ex.getMessage());
            }
        });

        btnCerrarSesion.addActionListener(e -> {
            empleado.cerrarSesion();
            JOptionPane.showMessageDialog(null, "Sesión cerrada. Hasta luego!");
            dispose();
        });

        cardLayout.show(panelContenido, "resultados");
    }
}
