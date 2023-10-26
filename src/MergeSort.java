import java.io.*;
import java.util.ArrayList;

public class MergeSort {
    // Merge Sort algorithm
    public static void mergeSort(ArrayList<UserData> userDataList) {
            if (userDataList.size() <= 1) {
                return; // Already sorted
            }
        // Calculate the average rating for each user
        for (UserData user : userDataList) {
            double averageRating = 0.0;

            // Assuming getRating() returns a single rating
            int rating = user.getRating();
            averageRating = rating;

            user.setAverageRating(averageRating);
        }

        // Split the list into two halves
        int mid = userDataList.size() / 2;
        ArrayList<UserData> left = new ArrayList<>(userDataList.subList(0, mid));
        ArrayList<UserData> right = new ArrayList<>(userDataList.subList(mid, userDataList.size()));

        // Recursively sort the two halves
        mergeSort(left);
        mergeSort(right);

        // Merge the sorted halves back into one sorted list
        merge(userDataList, left, right);
    }

    // Merge two sorted lists
    private static void merge(ArrayList<UserData> userDataList, ArrayList<UserData> left, ArrayList<UserData> right) {
        // Check for the empty case
        if (left.isEmpty()) {
            userDataList.addAll(right);
            return;
        }

        if (right.isEmpty()) {
            userDataList.addAll(left);
            return;
        }

        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getAverageRating() >= right.get(j).getAverageRating()) {
                userDataList.set(k, left.get(i));
                i++;
            } else {
                userDataList.set(k, right.get(j));
                j++;
            }
            k++;
        }

        while (i < left.size()) {
            userDataList.set(k, left.get(i));
            i++;
            k++;
        }

        while (j < right.size()) {
            userDataList.set(k, right.get(j));
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        // Call the readCSVFile method from RatingCounter to populate userDataList
        ArrayList<UserData> userDataList = RatingCounter.readCSVFile("src\\soc-sign-bitcoinotc.csv");

        // Call the mergeSort method to sort the data based on average_rating
        mergeSort(userDataList);

        // Write sorted data back to the CSV file
        writeSortedData("Rating-Results.csv", userDataList);

    }

    // Write sorted data back to the CSV file
    private static void writeSortedData(String outputFilePath, ArrayList<UserData> sortedData) {
        // Check if the output file already exists
        File outputFile = new File(outputFilePath);
        if (outputFile.exists()) {
            outputFile.delete();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("UserID, Average Rating\n");
            for (UserData user : sortedData) {
                writer.write(user.getUserID() + "," + String.format("%.3f", user.getAverageRating()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
