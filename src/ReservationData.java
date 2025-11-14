import java.io.*;
import java.util.*;

public class ReservationData {
    private static final String FILE_PATH = "data/reservations.txt";
    private List<Map<String, String>> reservations;

    public ReservationData() {
        reservations = new ArrayList<>();
        loadReservations();
    }

    public void addReservation(Map<String, String> reservation) {
        reservations.add(reservation);
        saveReservations();
    }

    public Map<String, String> getReservationByPNR(String pnr) {
        for (Map<String, String> res : reservations) {
            if (res.get("PNR").equals(pnr)) {
                return res;
            }
        }
        return null;
    }

    public boolean cancelReservation(String pnr) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).get("PNR").equals(pnr)) {
                reservations.remove(i);
                saveReservations();
                return true;
            }
        }
        return false;
    }

    private void saveReservations() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map<String, String> res : reservations) {
                StringBuilder line = new StringBuilder();
                line.append(res.get("PNR")).append("|")
                    .append(res.get("Name")).append("|")
                    .append(res.get("TrainNumber")).append("|")
                    .append(res.get("TrainName")).append("|")
                    .append(res.get("ClassType")).append("|")
                    .append(res.get("From")).append("|")
                    .append(res.get("To")).append("|")
                    .append(res.get("DateOfJourney"));
                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadReservations() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 8) {
                        Map<String, String> reservation = new HashMap<>();
                        reservation.put("PNR", parts[0]);
                        reservation.put("Name", parts[1]);
                        reservation.put("TrainNumber", parts[2]);
                        reservation.put("TrainName", parts[3]);
                        reservation.put("ClassType", parts[4]);
                        reservation.put("From", parts[5]);
                        reservation.put("To", parts[6]);
                        reservation.put("DateOfJourney", parts[7]);
                        reservations.add(reservation);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
