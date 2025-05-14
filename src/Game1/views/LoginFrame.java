package Game1.views;


import javax.swing.*;
import Game1.Controllers.GameController;
import Game1.Controllers.UserController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Game1.models.User;


public class LoginFrame extends JFrame {
    private UserController userController;
    private GameController gameController;

    public LoginFrame(UserController userController, GameController gameController) {
        this.userController = userController;
        this.gameController = gameController;
        initUI();
    }

    private void initUI() {
        setTitle("Klotski Puzzle - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton guestButton = new JButton("Play as Guest");

        loginButton.addActionListener(new ActionListener() {        //登录按钮检测
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                User user = userController.login(username, password);
                if (user != null) {
                    gameController.setCurrentUser(user);
                    openGameFrame();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(e -> {         //注册按钮
            RegisterFrame registerFrame = new RegisterFrame(userController);
            registerFrame.setVisible(true);
        });

        guestButton.addActionListener(e -> {        //游客模式按钮
            gameController.setCurrentUser(null);
            openGameFrame();
        });


        //GUI加上按钮
        panel.add(loginPanel);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(guestButton);

        add(panel);
    }

    private void openGameFrame() {
        GameFrame gameFrame = new GameFrame(gameController);
        gameFrame.setVisible(true);
        dispose();
    }
}