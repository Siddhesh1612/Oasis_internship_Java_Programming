package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Reservation;
import service.CancellationService;

public class CancellationFrame extends JFrame {

    private final CancellationService cancellationService;

    private JTextField pnrField;
    private JTextArea reservationDetailsArea;
    private Reservation currentReservation;

    public CancellationFrame() {
        cancellationService = new CancellationService();
        initializeFrame();
        createComponents();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Online Reservation System - Cancellation");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
    }

    private void createComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        headerPanel.setBackground(new Color(230, 236, 242));

        JLabel headerLabel = new JLabel("Cancellation Window");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 22));
        headerPanel.add(headerLabel, BorderLayout.WEST);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Search Reservation"));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        pnrField = new JTextField(18);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(event -> searchReservation());

        JButton cancelButton = new JButton("Cancel Reservation");
        cancelButton.addActionListener(event -> cancelReservation());

        JLabel noteLabel = new JLabel("Enter the PNR number to view reservation details first.");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        topPanel.add(noteLabel, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        topPanel.add(new JLabel("Enter PNR Number:"), constraints);

        constraints.gridx = 1;
        topPanel.add(pnrField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        topPanel.add(searchButton, constraints);

        constraints.gridx = 1;
        topPanel.add(cancelButton, constraints);

        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Steps"));
        infoPanel.add(new JLabel("1. Search by PNR."));
        infoPanel.add(new JLabel("2. Check the displayed reservation details."));
        infoPanel.add(new JLabel("3. Click Cancel Reservation to confirm."));

        reservationDetailsArea = new JTextArea(12, 35);
        reservationDetailsArea.setEditable(false);
        reservationDetailsArea.setLineWrap(true);
        reservationDetailsArea.setWrapStyleWord(true);
        reservationDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reservationDetailsArea.setText("Cancellation details will appear here after PNR search.");

        JScrollPane scrollPane = new JScrollPane(reservationDetailsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Reservation Information"));

        add(headerPanel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void searchReservation() {
        try {
            currentReservation = cancellationService.getReservationByPnr(pnrField.getText());
            reservationDetailsArea.setText(currentReservation.displayReservation());
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        } catch (IOException exception) {
            showError("Unable to read reservation details.");
        }
    }

    private void cancelReservation() {
        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {
            showError("PNR number is required.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this,
                "Do you want to cancel this reservation?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            currentReservation = cancellationService.cancelReservation(pnr);
            reservationDetailsArea.setText(currentReservation.displayReservation());
            JOptionPane.showMessageDialog(this, "Cancellation successful.");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        } catch (IOException exception) {
            showError("Unable to update reservation details.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Cancellation Error", JOptionPane.ERROR_MESSAGE);
    }
}
