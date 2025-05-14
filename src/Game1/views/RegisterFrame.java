package Game1.views;


import javax.swing.*;
import Game1.Controllers.UserController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class RegisterFrame extends JFrame {
    private UserController userController;

    public RegisterFrame(UserController userController) {
        this.userController = userController;
        initUI();
    }

    private void initUI() {
        setTitle("Klotski Puzzle - Register");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userController.register(username, password)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterFrame.this,
                            "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(registerButton);
        panel.add(cancelButton);

        add(panel);
    }
}