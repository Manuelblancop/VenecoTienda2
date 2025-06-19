package GUI;
import javax.swing.*;

import DLL.AuthController;

import java.awt.*;
import java.awt.event.*;

public class LoginRegisterFrame extends JFrame {
    private JTextField loginUserField;
    private JPasswordField loginPasswordField;
    private JComboBox<String> roleComboBox;
    private JTextField registerUserField;
    private JPasswordField registerPasswordField;
    private JTextField registerEmailField;

    public LoginRegisterFrame() {
        setTitle("Login y Registro");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        ///////////////////////////////////////////////////////////// Panel Login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setBounds(50, 30, 80, 25);
        loginPanel.add(userLabel);

        loginUserField = new JTextField();
        loginUserField.setBounds(150, 30, 150, 25);
        loginPanel.add(loginUserField);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setBounds(50, 70, 80, 25);
        loginPanel.add(passLabel);

        loginPasswordField = new JPasswordField();
        loginPasswordField.setBounds(150, 70, 150, 25);
        loginPanel.add(loginPasswordField);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(150, 110, 130, 30);
        loginPanel.add(loginButton);

        loginButton.addActionListener(e -> handleLogin());

        /////////////////////////////////////////////////// Panel Registro
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(null);

        JLabel regUserLabel = new JLabel("Usuario:");
        regUserLabel.setBounds(50, 30, 80, 25);
        registerPanel.add(regUserLabel);

        registerUserField = new JTextField();
        registerUserField.setBounds(150, 30, 150, 25);
        registerPanel.add(registerUserField);

        JLabel regPassLabel = new JLabel("Contraseña:");
        regPassLabel.setBounds(50, 70, 80, 25);
        registerPanel.add(regPassLabel);

        registerPasswordField = new JPasswordField();
        registerPasswordField.setBounds(150, 70, 150, 25);
        registerPanel.add(registerPasswordField);

        JLabel regEmailLabel = new JLabel("Email:");
        regEmailLabel.setBounds(50, 110, 80, 25);
        registerPanel.add(regEmailLabel);

        registerEmailField = new JTextField();
        registerEmailField.setBounds(150, 110, 150, 25);
        registerPanel.add(registerEmailField);
        
        JLabel roleLabel = new JLabel("Rol:");
        roleLabel.setBounds(50, 150, 80, 25);
        registerPanel.add(roleLabel);

        String[] roles = {"admin", "cliente", "empleado", "repartidor"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(150, 150, 150, 25);
        registerPanel.add(roleComboBox);


        JButton registerButton = new JButton("Registrarse");
        registerButton.setBounds(150, 150, 130, 30);
        registerPanel.add(registerButton);

        registerButton.addActionListener(e -> handleRegister());

        // Agregar paneles al tabbedPane
        tabbedPane.addTab("Iniciar sesión", loginPanel);
        tabbedPane.addTab("Registrarse", registerPanel);

        add(tabbedPane);
    }

    private void handleLogin() {
        String username = loginUserField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aquí usarías tu clase Login para validar
        boolean loginExitoso = AuthController.login(username, password);
        if (loginExitoso) {
            JOptionPane.showMessageDialog(this, "Login exitoso");
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























