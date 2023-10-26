import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RatingCounter {
    public static void main(String[] args) {
        // Read the CSV file and store user data
        ArrayList<UserData> userDataList = readCSVFile("src\\soc-sign-bitcoinotc.csv");

        // Calculate the average rating for each user
        Map<Integer, Double> averageRatings = calculateAverageRatings(userDataList);

        // Output the average ratings to RatingResults.csv
        outputAverageRatings("RatingResults.csv", averageRatings);
    }

    // Read the CSV file and store user data in an ArrayList
    public static ArrayList<UserData> readCSVFile(String filePath) {
        ArrayList<UserData> userDataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length < 3) {
                    System.out.println("Skipping line with insufficient data: " + line);
                    continue;
                }
                int source = Integer.parseInt(row[0]);
                int target = Integer.parseInt(row[1]);
                int rating = Integer.parseInt(row[2]);
                // In the next line, you should pass the actual user ID, which in your case is 'target.'
                UserData user = new UserData(target, rating);
                userDataList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDataList;
    }

    // Calculate the average rating for each user
    private static Map<Integer, Double> calculateAverageRatings(ArrayList<UserData> userDataList) {
        Map<Integer, Double> userAverageRatings = new HashMap<>();
        Map<Integer, Integer> userRatingCounts = new HashMap<>();

        for (UserData user : userDataList) {
            int userID = user.getUserID();
            double rating = user.getRating();

            // Update the total rating and rating count for each user
            userAverageRatings.put(userID, userAverageRatings.getOrDefault(userID, 0.0) + rating);
            userRatingCounts.put(userID, userRatingCounts.getOrDefault(userID, 0) + 1);
        }

        // Calculate the average rating for each user
        for (int userID : userAverageRatings.keySet()) {
            double totalRating = userAverageRatings.get(userID);
            int ratingCount = userRatingCounts.get(userID);
            double averageRating = totalRating / ratingCount;
            userAverageRatings.put(userID, averageRating);
        }

        return userAverageRatings;
    }

    // Output the average ratings to a CSV file
    private static void outputAverageRatings(String outputFilePath, Map<Integer, Double> averageRatings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Write headers
            writer.write("UserID, Average Rating\n");

            for (int userID : averageRatings.keySet()) {
                double averageRating = averageRatings.get(userID);
                writer.write(userID + "," + String.format("%.3f", averageRating) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
