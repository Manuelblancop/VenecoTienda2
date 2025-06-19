package venecotienda;

import javax.swing.SwingUtilities;
import GUI.LoginRegisterFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginRegisterFrame loginFrame = new LoginRegisterFrame();
            loginFrame.setVisible(true);
        });
    }
}
