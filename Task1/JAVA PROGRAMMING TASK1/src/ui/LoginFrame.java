package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import service.LoginService;

public class LoginFrame extends JFrame {

    private final LoginService loginService;
    private JTextField loginIdField;
    private JPasswordField passwordField;

    public LoginFrame() {
        loginService = new LoginService();
        initializeFrame();
        createComponents();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Online Reservation System - Login");
        setSize(460, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void createComponents() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        titlePanel.setBackground(new Color(230, 236, 242));

        JLabel headingLabel = new JLabel("Railway Online Reservation System", JLabel.CENTER);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titlePanel.add(headingLabel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel infoLabel = new JLabel("Use demo login: user / 1234");
        JLabel loginIdLabel = new JLabel("Login ID:");
        JLabel passwordLabel = new JLabel("Password:");

        loginIdField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(event -> handleLogin());
        getRootPane().setDefaultButton(loginButton);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(titleLabel, constraints);

        constraints.gridy = 1;
        panel.add(infoLabel, constraints);

        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(loginIdLabel, constraints);

        constraints.gridx = 1;
        panel.add(loginIdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        add(titlePanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }

    private void handleLogin() {
        String loginId = loginIdField.getText();
        String password = new String(passwordField.getPassword());

        if (loginService.validateLogin(loginId, password)) {
            JOptionPane.showMessageDialog(this, "Login successful.");
            new ReservationFrame();
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Invalid login ID or password.", "Login Failed",
                JOptionPane.ERROR_MESSAGE);
    }
}
