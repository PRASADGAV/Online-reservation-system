import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel messageLabel;

    public LoginForm() {
        setTitle("Online Reservation System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("LOGIN FORM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        userIdField = new JTextField();
        userIdField.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setBackground(new Color(0, 150, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setBackground(new Color(200, 0, 0));
        clearButton.setForeground(Color.WHITE);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title (span 2 columns)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // User ID
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(userIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(userIdField, gbc);

        // Password
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Message (span 2 columns)
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        // Buttons (centered, span 2 columns)
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        panel.add(buttonPanel, gbc);

        add(panel);

        // Pack and center instead of fixed size so layout adapts to space
        pack();
        setLocationRelativeTo(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userIdField.setText("");
                passwordField.setText("");
                messageLabel.setText("");
            }
        });

        setVisible(true);
    }

    private void handleLogin() {
        String userId = userIdField.getText();
        String password = new String(passwordField.getPassword());

        // Hardcoded user for demo (In real system, check with database)
        User user = new User("admin", "123456");

        if (user.authenticate(userId, password)) {
            messageLabel.setForeground(new Color(0, 150, 0));
            messageLabel.setText("Login Successful!");
            dispose();
            new ReservationForm();
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Invalid User ID or Password!");
            passwordField.setText("");
        }
    }
}
