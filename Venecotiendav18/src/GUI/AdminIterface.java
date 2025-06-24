package GUI;
import clases.Admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminIterface extends JFrame {

		private static final long serialVersionUID = 1L;
		private Admin admin;
	    private JTextArea areaResultados;
	    private JButton btnVerPedidos, btnVerProductos, btnVerEstadoPedidos, btnEliminarProducto, btnEditarPerfil, btnCerrarSesion;

	    public AdminIterface(Admin admin) {
	        this.admin = admin;

	        // Configuración del JFrame
	        setTitle("Panel de Administrador");
	        setSize(750, 500);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        getContentPane().setLayout(new BorderLayout());

	        // Área de resultados a la derecha
	        areaResultados = new JTextArea();
	        areaResultados.setEditable(false);
	        areaResultados.setBackground(new Color(9, 22, 42));
	        areaResultados.setForeground(Color.WHITE);
	        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
	        areaResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        JScrollPane scrollPane = new JScrollPane(areaResultados);
	        getContentPane().add(scrollPane, BorderLayout.CENTER);

	        // Panel izquierdo de botones
	        JPanel panelBotones = new JPanel();
	        panelBotones.setLayout(new GridLayout(6, 1, 10, 10));
	        panelBotones.setBackground(new Color(25, 39, 70));
	        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        getContentPane().add(panelBotones, BorderLayout.WEST);

	        btnVerPedidos = new JButton("Ver Pedidos Asignados");
	        btnVerProductos = new JButton("Ver Productos");
	        btnVerEstadoPedidos = new JButton("Ver Estado de Pedidos");
	        btnEliminarProducto = new JButton("Eliminar Producto");
	        btnEditarPerfil = new JButton("Editar Perfil");
	        btnCerrarSesion = new JButton("Cerrar Sesión");

	        // Estilo uniforme
	        JButton[] botones = {btnVerPedidos, btnVerProductos, btnVerEstadoPedidos, btnEliminarProducto, btnEditarPerfil, btnCerrarSesion};
	        for (JButton btn : botones) {
	            btn.setFocusPainted(false);
	            btn.setBackground(new Color(255, 195, 0));
	            btn.setForeground(Color.BLACK);
	            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
	            panelBotones.add(btn);
	        }
	        
	        // Eventos de botones
	        btnVerPedidos.addActionListener(e -> areaResultados.setText(admin.verPedidosAsignados()));
	        btnVerProductos.addActionListener(e -> areaResultados.setText(admin.verProductos()));
	        // Corregir
	        btnVerEstadoPedidos.addActionListener(e -> areaResultados.setText(admin.verEstadoPedidos(getDefaultCloseOperation())));
	        // Corregir
	        btnEliminarProducto.addActionListener(e -> areaResultados.setText(admin.eliminarProducto(getDefaultCloseOperation())));
	        // NO DARLE CLICK, TIENE UN ERROR QUE MODIFICA USUARIO Y CONTRASEÑA DEL ADMIN
	        btnEditarPerfil.addActionListener(e -> areaResultados.setText(admin.editarPerfil(getTitle(), getTitle())));

	        btnCerrarSesion.addActionListener(e -> {
	            admin.cerrarSesion();
	            JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!");
	            dispose();
	        });
	    

        ///////////////////////////////////////Agregar los eventos de los botones
        btnVerPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.verPedidosAsignados();
            }
        });

        btnVerEstadoPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.verEstadoPedidos(getDefaultCloseOperation());
            }
        });

        btnEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.eliminarProducto(getDefaultCloseOperation());
            }
        });

        btnEditarPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.editarPerfil(getTitle(), getTitle());
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
	}


