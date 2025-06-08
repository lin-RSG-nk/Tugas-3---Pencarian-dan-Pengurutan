import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String file = "camera_dataset.csv";
        DatasetCamera[] listCamera = new DatasetCamera[1038];
        Scanner input = new Scanner(System.in);
        if (!file.endsWith(".csv")) {
            System.out.println("File harus berformat CSV !!");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String baris;
            int idx = 0;

            while ((baris = br.readLine()) != null) {
                String[] data = baris.split(",");
                if (data.length < 13) {
                    System.out.println("Baris tidak valid, dilewati.");
                    continue;
                }

                try {
                    DatasetCamera camera = new DatasetCamera(
                            data[0],
                            parseIntOrDefault(data[1], 1),
                            parseDoubleOrDefault(data[2], 1.0),
                            parseDoubleOrDefault(data[3], 1.0),
                            parseDoubleOrDefault(data[4], 1.0),
                            parseDoubleOrDefault(data[5], 1.0),
                            parseDoubleOrDefault(data[6], 1.0),
                            parseDoubleOrDefault(data[7], 1.0),
                            parseDoubleOrDefault(data[8], 1.0),
                            parseDoubleOrDefault(data[9], 1.0),
                            parseDoubleOrDefault(data[10], 1.0),
                            parseDoubleOrDefault(data[11], 1.0),
                            parseDoubleOrDefault(data[12], 1.0)
                    );
                    listCamera[idx++] = camera;
                } catch (Exception e) {
                    System.out.println("Error parsing angka: " + e.getMessage());
                }
            }
            System.out.println("=== 5 Kamera Pertama ===");
            for (int i = 0; i < 5 && i < listCamera.length; i++) {
                listCamera[i].printInfo();
            }

            //Linear Search
            System.out.print("Masukkan nama model kamera yang ingin dicari: ");
            String key = input.nextLine();
            boolean found = false;

            for (int i = 0; i < listCamera.length; i++) {
                if (listCamera[i] != null && listCamera[i].getModel().equalsIgnoreCase(key)) {
                    found = true;
                    listCamera[i].printInfo(true);
                    break;
                }
            }
            if (!found){
                System.out.println("Kamera tidak di temukan");
            }

            // Bubble Sort
            for (int i = 0; i < idx - 1; i++) {
                for (int j = 0; j < idx - i - 1; j++) {
                    if (listCamera[j].getPrice() > listCamera[j + 1].getPrice()) {
                        DatasetCamera temp = listCamera[j];
                        listCamera[j] = listCamera[j + 1];
                        listCamera[j + 1] = temp;
                    }
                }
            }
            System.out.println("=== 5 Kamera Termurah ===");
            for (int i = 0; i < 5 && i < idx; i++) {
                listCamera[i].printInfo();
            }

        } catch (Exception e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }
    }

    private static double parseDoubleOrDefault(String value, double defaultValue) {
        return (value == null || value.trim().isEmpty()) ? defaultValue : Double.parseDouble(value.trim());
    }

    private static int parseIntOrDefault(String value, int defaultValue) {
        return (value == null || value.trim().isEmpty()) ? defaultValue : Integer.parseInt(value.trim());
    }
}
