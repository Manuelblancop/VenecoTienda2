package GUI;
import javax.swing.*;
import java.awt.*;
import DLL.AuthController;
import clases.Admin;
import clases.Empleado;
import clases.Repartidor;
import clases.Usuario;
import singleton.Sesion;

public class Login extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField loginUserField;
    private JPasswordField loginPasswordField;
    private JComboBox<String> roleComboBox;
    private JTextField registerUserField;
    private JPasswordField registerPasswordField;
    private JTextField registerEmailField;
    public Login() {
    	setResizable(false);
        setTitle("Login y Registro");
        setSize(437, 543);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        //Color del fondo
        Color azulOscuro = Color.decode("#09162a");

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 420, 500);
        

        ///////////////////////////////////////////////////////////// Panel Login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(azulOscuro);
        
        JLabel iconLogin = new JLabel(new ImageIcon(Login.class.getResource("/img/login.png")));
        iconLogin.setBounds(150, 30, 100, 100);
        loginPanel.add(iconLogin);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        userLabel.setBounds(90, 220, 74, 25);
        loginPanel.add(userLabel);

        loginUserField = new JTextField();
        loginUserField.setBounds(169, 217, 154, 21);
        loginUserField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(loginUserField);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        passLabel.setBounds(75, 262, 84, 25);
        loginPanel.add(passLabel);

        loginPasswordField = new JPasswordField();
        loginPasswordField.setBounds(169, 266, 154, 21);
        loginPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loginPanel.add(loginPasswordField);

        JButton loginButton = new JButton("INICIAR SESIÓN");
        loginButton.setBounds(117, 335, 172, 37);
        loginButton.setBackground(new Color(239, 210, 143));
        loginButton.setForeground(azulOscuro);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginPanel.add(loginButton);

        loginButton.addActionListener(e -> handleLogin());

        /////////////////////////////////////////////////// Panel Registro
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(null);
        registerPanel.setBackground(azulOscuro);

        JLabel iconRegister = new JLabel(new ImageIcon(Login.class.getResource("/img/registro.png")));
        iconRegister.setBounds(154, 48, 100, 100);
        registerPanel.add(iconRegister);

        JLabel regUserLabel = new JLabel("Usuario:");
        regUserLabel.setForeground(Color.WHITE);
        regUserLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        regUserLabel.setBounds(53, 183, 80, 25);
        registerPanel.add(regUserLabel);

        registerUserField = new JTextField();
        registerUserField.setBounds(153, 183, 180, 25);
        registerUserField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerPanel.add(registerUserField);

        JLabel regPassLabel = new JLabel("Contraseña:");
        regPassLabel.setForeground(Color.WHITE);
        regPassLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        regPassLabel.setBounds(53, 223, 80, 25);
        registerPanel.add(regPassLabel);

        registerPasswordField = new JPasswordField();
        registerPasswordField.setBounds(153, 223, 180, 25);
        registerPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerPanel.add(registerPasswordField);

        JLabel regEmailLabel = new JLabel("Email:");
        regEmailLabel.setForeground(Color.WHITE);
        regEmailLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        regEmailLabel.setBounds(53, 263, 80, 25);
        registerPanel.add(regEmailLabel);

        registerEmailField = new JTextField();
        registerEmailField.setBounds(153, 263, 180, 25);
        registerEmailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        registerPanel.add(registerEmailField);

        JLabel roleLabel = new JLabel("Rol:");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        roleLabel.setBounds(53, 303, 80, 25);
        registerPanel.add(roleLabel);

        String[] roles = {"cliente", "empleado", "repartidor"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(153, 303, 180, 25);
        registerPanel.add(roleComboBox);

        JButton registerButton = new JButton("REGISTRARSE");
        registerButton.setBounds(153, 380, 154, 36);
        registerButton.setBackground(new Color(239, 210, 143));
        registerButton.setForeground(azulOscuro);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setFocusPainted(false);
        registerPanel.add(registerButton);

        registerButton.addActionListener(e -> handleRegister());
        tabbedPane.addTab("Iniciar sesión", loginPanel);
        
        JLabel lblNewLabel = new JLabel("INICIAR SESIÓN");
        lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(131, 139, 158, 37);
        loginPanel.add(lblNewLabel);
        tabbedPane.addTab("Registrarse", registerPanel);

        getContentPane().add(tabbedPane);
        setVisible(true);
    }

    private void handleLogin() {
        String username = loginUserField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean loginExitoso = AuthController.login(username, password);

        if (loginExitoso) {
            Usuario user = Sesion.getInstancia().getUsuarioActual();
            
            if (user instanceof Admin) {
                AdminIterface adminFrame = new AdminIterface((Admin) user);
                adminFrame.setVisible(true);
                dispose(); // Cerramos la ventana de login
            } 
            else if (user instanceof Repartidor) {
                RepartidorInterface repartidorFrame = new RepartidorInterface((Repartidor) user);
                repartidorFrame.setVisible(true);
                dispose(); // Cerramos la ventana de login
            }
            else if (user instanceof Empleado) {
            	EmpleadoInterface empleadoFrame = new EmpleadoInterface((Empleado) user);
                empleadoFrame.setVisible(true);
                dispose(); // Cerramos la ventana de login
            }
            else {
                JOptionPane.showMessageDialog(this, "Tipo de usuario no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void handleRegister() {
        String username = registerUserField.getText().trim();
        String password = new String(registerPasswordField.getPassword());
        String email = registerEmailField.getText().trim();
        String rol = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || rol == null) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Email inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean creado = AuthController.registrar(username, password, rol);
        if (creado) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito como " + rol);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


