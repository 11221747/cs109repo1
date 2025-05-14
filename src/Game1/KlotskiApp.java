package Game1;

import javax.swing.*;

import Game1.Controllers.GameController;
import Game1.Controllers.UserController;
import Game1.views.LoginFrame;


public class KlotskiApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            UserController userController = new UserController();
            GameController gameController = new GameController();

            LoginFrame loginFrame = new LoginFrame(userController, gameController);
            loginFrame.setVisible(true);
        });
    }
}
