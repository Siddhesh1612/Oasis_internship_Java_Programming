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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Reservation;
import service.ReservationService;
import service.TrainService;

public class ReservationFrame extends JFrame {

    private final ReservationService reservationService;
    private final TrainService trainService;

    private JTextField passengerNameField;
    private JComboBox<String> trainNumberComboBox;
    private JTextField trainNameField;
    private JComboBox<String> classTypeComboBox;
    private JTextField dateOfJourneyField;
    private JTextField sourceField;
    private JTextField destinationField;
    private JTextArea resultArea;

    public ReservationFrame() {
        reservationService = new ReservationService();
        trainService = reservationService.getTrainService();
        initializeFrame();
        createComponents();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Online Reservation System - Dashboard");
        setSize(940, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
    }

    private void createComponents() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        headerPanel.setBackground(new Color(230, 236, 242));

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        JLabel subTitleLabel = new JLabel("Reserve ticket or open cancellation using the options below.");
        subTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel titleWrapper = new JPanel();
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.Y_AXIS));
        titleWrapper.setOpaque(false);
        titleWrapper.add(titleLabel);
        titleWrapper.add(subTitleLabel);

        JButton openCancellationButton = new JButton("Open Cancellation");
        openCancellationButton.addActionListener(event -> new CancellationFrame());

        headerPanel.add(titleWrapper, BorderLayout.WEST);
        headerPanel.add(openCancellationButton, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Reservation Form"));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;

        passengerNameField = new JTextField(18);
        trainNumberComboBox = new JComboBox<>(trainService.getTrainNumbers());
        trainNameField = new JTextField(18);
        trainNameField.setEditable(false);
        classTypeComboBox = new JComboBox<>(new String[] { "Sleeper", "AC", "First Class" });
        dateOfJourneyField = new JTextField(18);
        sourceField = new JTextField(18);
        destinationField = new JTextField(18);

        trainNumberComboBox.addActionListener(event -> updateTrainName());

        JLabel formNoteLabel = new JLabel("Fill all fields to confirm the reservation.");
        formNoteLabel.setHorizontalAlignment(SwingConstants.LEFT);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        formPanel.add(formNoteLabel, constraints);

        addFormRow(formPanel, constraints, 1, "Passenger Name:", passengerNameField);
        addFormRow(formPanel, constraints, 2, "Train Number:", trainNumberComboBox);
        addFormRow(formPanel, constraints, 3, "Train Name:", trainNameField);
        addFormRow(formPanel, constraints, 4, "Class Type:", classTypeComboBox);
        addFormRow(formPanel, constraints, 5, "Date of Journey (YYYY-MM-DD):", dateOfJourneyField);
        addFormRow(formPanel, constraints, 6, "Source:", sourceField);
        addFormRow(formPanel, constraints, 7, "Destination:", destinationField);

        JButton reserveButton = new JButton("Reserve Ticket");
        reserveButton.addActionListener(event -> reserveTicket());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(event -> clearForm());

        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 1;
        formPanel.add(reserveButton, constraints);

        constraints.gridx = 1;
        formPanel.add(clearButton, constraints);

        JPanel summaryPanel = new JPanel(new BorderLayout(10, 10));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Reservation Output"));

        JPanel helpPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        helpPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        helpPanel.add(new JLabel("1. Enter passenger name."));
        helpPanel.add(new JLabel("2. Select train number."));
        helpPanel.add(new JLabel("3. Enter journey date, source and destination."));
        helpPanel.add(new JLabel("4. Click Reserve Ticket."));
        helpPanel.add(new JLabel("5. PNR and reservation details will appear here."));

        resultArea = new JTextArea(14, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        resultArea.setText("Welcome to the Online Reservation System.\n\n"
                + "Complete the form on the left side to book a ticket.");

        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        resultScrollPane.setBorder(BorderFactory.createTitledBorder("Reservation Details"));

        summaryPanel.add(helpPanel, BorderLayout.NORTH);
        summaryPanel.add(resultScrollPane, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        centerPanel.add(formPanel);
        centerPanel.add(summaryPanel);
        add(centerPanel, BorderLayout.CENTER);

        updateTrainName();
    }

    private void addFormRow(JPanel panel, GridBagConstraints constraints, int row, String labelText, Object field) {
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        panel.add(new JLabel(labelText), constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;

        if (field instanceof JTextField) {
            panel.add((JTextField) field, constraints);
        } else if (field instanceof JComboBox) {
            panel.add((JComboBox<?>) field, constraints);
        }
    }

    private void updateTrainName() {
        String trainNumber = (String) trainNumberComboBox.getSelectedItem();
        trainNameField.setText(trainService.getTrainName(trainNumber));
    }

    private void reserveTicket() {
        try {
            Reservation reservation = reservationService.reserveTicket(
                    passengerNameField.getText(),
                    (String) trainNumberComboBox.getSelectedItem(),
                    (String) classTypeComboBox.getSelectedItem(),
                    dateOfJourneyField.getText(),
                    sourceField.getText(),
                    destinationField.getText());

            resultArea.setText("Reservation Successful\n\n" + reservation.displayReservation());
            JOptionPane.showMessageDialog(this, "Reservation successful.");
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(this, "Unable to save reservation details.", "Storage Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        passengerNameField.setText("");
        trainNumberComboBox.setSelectedIndex(0);
        classTypeComboBox.setSelectedIndex(0);
        dateOfJourneyField.setText("");
        sourceField.setText("");
        destinationField.setText("");
        resultArea.setText("Form cleared.\n\nEnter new reservation details to continue.");
        updateTrainName();
    }
}
