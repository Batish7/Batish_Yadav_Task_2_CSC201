import java.io.*;

public class CSVReader {
    public static void main(String[] args) {
        String file = "src\\soc-sign-bitcoinotc.csv";

        String line = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                // Format the output using printf
                System.out.printf("%-10s, %-10s, %-10s%n", "Source:" + row[0], "Target:" + row[1], "Rating:" + row[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
