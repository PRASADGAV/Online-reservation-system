import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ReservationForm extends JFrame {
    private JTextField nameField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JComboBox<String> classTypeBox;
    private JTextField fromField;
    private JTextField toField;
    private JTextField dateField;
    private JButton submitButton;
    private JButton resetButton;
    private JButton cancelTicketButton;
    private JLabel messageLabel;
    private ReservationData reservationData;

    public ReservationForm() {
        reservationData = new ReservationData();

        setTitle("Online Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Initialize components (must be created before layout)
        nameField = new JTextField();
        trainNumberField = new JTextField();
        trainNameField = new JTextField();
        classTypeBox = new JComboBox<>(new String[] {"First AC", "Second AC", "Sleeper", "General"});
        fromField = new JTextField();
        toField = new JTextField();
        dateField = new JTextField();
        submitButton = new JButton("RESERVE");
        resetButton = new JButton("RESET");
        cancelTicketButton = new JButton("CANCEL TICKET");
        messageLabel = new JLabel("");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));
        panel.setBackground(new Color(245,245,245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Title spanning two columns
        JLabel title = new JLabel("RESERVATION FORM", SwingConstants.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = row++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(title, gbc);
        gbc.gridwidth = 1; // restore

        // Add rows (use helper method moved to class level)
        addRow(panel, gbc, row++, new JLabel("Passenger Name:"), nameField, 1.0);
        addRow(panel, gbc, row++, new JLabel("Train Number:"), trainNumberField, 1.0);
        addRow(panel, gbc, row++, new JLabel("Train Name:"), trainNameField, 1.0);
        addRow(panel, gbc, row++, new JLabel("Class Type:"), classTypeBox, 1.0);
        addRow(panel, gbc, row++, new JLabel("From (Place):"), fromField, 1.0);
        addRow(panel, gbc, row++, new JLabel("To (Destination):"), toField, 1.0);
        addRow(panel, gbc, row++, new JLabel("Date of Journey (DD/MM/YYYY):"), dateField, 1.0);

        // Buttons row (span two columns, centered)
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(submitButton);
        btnPanel.add(resetButton);
        btnPanel.add(cancelTicketButton);

        gbc.gridy = row++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnPanel, gbc);

        // Preferred sizes
        Dimension fieldPref = new Dimension(220, 28);
        for (JComponent c : new JComponent[]{nameField, trainNumberField, trainNameField, fromField, toField, dateField}) {
            c.setPreferredSize(fieldPref);
        }
        classTypeBox.setPreferredSize(new Dimension(140, 28));

        getContentPane().removeAll();
        add(panel);
        pack();
        setLocationRelativeTo(null);

        submitButton.addActionListener(e -> handleReservation());
        resetButton.addActionListener(e -> clearFields());
        cancelTicketButton.addActionListener(e -> new CancellationForm(reservationData));

        setVisible(true);
    }

    // helper moved to class level (legal in Java)
    private void addRow(JPanel panel, GridBagConstraints gbc, int row, JLabel lbl, JComponent comp, double weightX) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = weightX;
        panel.add(comp, gbc);
    }

    private void handleReservation() {
        String name = nameField.getText().trim();
        String trainNumber = trainNumberField.getText().trim();
        String trainName = trainNameField.getText().trim();
        String classType = (String) classTypeBox.getSelectedItem();
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        String dateOfJourney = dateField.getText().trim();

        if (name.isEmpty() || trainNumber.isEmpty() || trainName.isEmpty() || 
            from.isEmpty() || to.isEmpty() || dateOfJourney.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("All fields are required!");
            return;
        }

        String pnr = generatePNR();

        Map<String, String> reservation = new HashMap<>();
        reservation.put("PNR", pnr);
        reservation.put("Name", name);
        reservation.put("TrainNumber", trainNumber);
        reservation.put("TrainName", trainName);
        reservation.put("ClassType", classType);
        reservation.put("From", from);
        reservation.put("To", to);
        reservation.put("DateOfJourney", dateOfJourney);

        reservationData.addReservation(reservation);

        messageLabel.setForeground(new Color(0, 150, 0));
        messageLabel.setText("Reservation Successful! PNR: " + pnr);
        clearFields();
    }

    private void clearFields() {
        nameField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        classTypeBox.setSelectedIndex(0);
        fromField.setText("");
        toField.setText("");
        dateField.setText("");
        messageLabel.setText("");
    }

    private String generatePNR() {
        Random random = new Random();
        return String.format("PNR%d", 100000 + random.nextInt(900000));
    }
}
