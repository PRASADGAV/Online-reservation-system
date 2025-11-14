import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CancellationForm extends JFrame {
    private JTextField pnrField;
    private JButton searchButton;
    private JButton confirmButton;
    private JButton okButton;
    private JLabel messageLabel;
    private JTextArea detailsArea;
    private ReservationData reservationData;
    private String currentPNR;

    public CancellationForm(ReservationData reservationData) {
        this.reservationData = reservationData;

        setTitle("Cancellation Form");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2, 10, 10));
        searchPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("CANCELLATION FORM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel pnrLabel = new JLabel("Enter PNR Number:");
        pnrLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        pnrField = new JTextField();
        pnrField.setFont(new Font("Arial", Font.PLAIN, 12));

        searchButton = new JButton("SEARCH");
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setBackground(new Color(0, 150, 150));
        searchButton.setForeground(Color.WHITE);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 10));

        searchPanel.add(titleLabel);
        searchPanel.add(new JLabel());
        searchPanel.add(pnrLabel);
        searchPanel.add(pnrField);
        searchPanel.add(searchButton);
        searchPanel.add(messageLabel);

        detailsArea = new JTextArea();
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 11));
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(detailsArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 240));

        confirmButton = new JButton("CONFIRM CANCELLATION");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 12));
        confirmButton.setBackground(new Color(200, 0, 0));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.setEnabled(false);

        okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 12));
        okButton.setBackground(new Color(100, 100, 100));
        okButton.setForeground(Color.WHITE);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(confirmButton);
        buttonPanel.add(okButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancellation();
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void handleSearch() {
        String pnr = pnrField.getText().trim();

        if (pnr.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please enter PNR number!");
            detailsArea.setText("");
            confirmButton.setEnabled(false);
            return;
        }

        Map<String, String> reservation = reservationData.getReservationByPNR(pnr);

        if (reservation == null) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("No reservation found for this PNR!");
            detailsArea.setText("");
            confirmButton.setEnabled(false);
        } else {
            currentPNR = pnr;
            messageLabel.setForeground(new Color(0, 150, 0));
            messageLabel.setText("Reservation found!");

            StringBuilder details = new StringBuilder();
            details.append("PNR Number: ").append(reservation.get("PNR")).append("\n")
                   .append("Passenger Name: ").append(reservation.get("Name")).append("\n")
                   .append("Train Number: ").append(reservation.get("TrainNumber")).append("\n")
                   .append("Train Name: ").append(reservation.get("TrainName")).append("\n")
                   .append("Class Type: ").append(reservation.get("ClassType")).append("\n")
                   .append("From: ").append(reservation.get("From")).append("\n")
                   .append("To: ").append(reservation.get("To")).append("\n")
                   .append("Date of Journey: ").append(reservation.get("DateOfJourney"));

            detailsArea.setText(details.toString());
            confirmButton.setEnabled(true);
        }
    }

    private void handleCancellation() {
        if (reservationData.cancelReservation(currentPNR)) {
            messageLabel.setForeground(new Color(0, 150, 0));
            messageLabel.setText("Cancellation Successful!");
            detailsArea.setText("");
            pnrField.setText("");
            confirmButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Your ticket has been cancelled successfully.", 
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Error cancelling reservation!");
        }
    }
}
