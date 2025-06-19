package venecotienda;

import javax.swing.SwingUtilities;
import GUI.Login;



public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	Login loginFrame = new Login();
            loginFrame.setVisible(true);
        });
    }
}
