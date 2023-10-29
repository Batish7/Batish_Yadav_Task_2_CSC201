import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class BubbleSort {// Read the CSV file and store user data in an ArrayList
    public static void main(String[] args) {
        String inputFile = "D:\\2nd Year sem 1\\ICT 221\\Task_2_CSC201\\RatingResults.csv";  // Replace with your CSV file path
        String outputFile = "BubbleSort.csv";  // Replace with the desired output file path

        // Read the CSV file and store user data in an ArrayList
        ArrayList<UserData> userDataList = readCSVFile(inputFile);

        // Calculate and set the average rating for each user
        calculateAverageRatings(userDataList);

        // Sort the user data using Bubble Sort based on Average Rating
        bubbleSort(userDataList);

        // Write the sorted data back to a new CSV file
        writeCSVFile(outputFile, userDataList);

        // Call the method to print users with the 4th highest average rating (7.5)
        printUsersWithNthHighestAverageRating(4, userDataList);
    } public static void bubbleSort(ArrayList<UserData> userDataList) {
        int n = userDataList.size();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (userDataList.get(i).getAverageRating() < userDataList.get(i + 1).getAverageRating()) {
                    // Swap userData at position i and i+1
                    UserData temp = userDataList.get(i);
                    userDataList.set(i, userDataList.get(i + 1));
                    userDataList.set(i + 1, temp);
                    swapped = true;
                }
            }
        } while (swapped);
    }public static ArrayList<UserData> readCSVFile(String filePath) {
        ArrayList<UserData> userDataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; // To skip the first line (headers)
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line
                }
                String[] row = line.split(",");
                if (row.length < 2) {
                    System.out.println("Skipping line with insufficient data: " + line);
                    continue;
                }
                int userID = Integer.parseInt(row[0]);
                double rating = Double.parseDouble(row[1]); // Parse the rating as double
                UserData user = new UserData(userID, rating);
                userDataList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDataList;
    }

    public static void writeCSVFile(String filePath, ArrayList<UserData> userDataList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("User_ID, Rating\n");

            for (UserData user : userDataList) {
                writer.write(user.getUserID() + "," + user.getRating() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void calculateAverageRatings(ArrayList<UserData> userDataList) {
        for (UserData user : userDataList) {
            int userID = user.getUserID();
            double totalRating = user.getRating();
            int count = 1;

            for (UserData otherUser : userDataList) {
                if (otherUser.getUserID() == userID && otherUser != user) {
                    totalRating += otherUser.getRating();
                    count++;
                }
            }

            double averageRating = totalRating / count;
            user.setAverageRating(averageRating);
        }
    }
    public static void printUsersWithNthHighestAverageRating(int nthRank, ArrayList<UserData> userDataList) {
        ArrayList<Double> uniqueRatings = new ArrayList<>();

        for (UserData user : userDataList) {
            double averageRating = user.getAverageRating();
            if (!uniqueRatings.contains(averageRating)) {
                uniqueRatings.add(averageRating);
            }
        }

        if (nthRank > uniqueRatings.size() || nthRank <= 0) {
            System.out.println("Invalid rank.");
            return;
        }

        // Sort the unique ratings in descending order
        Collections.sort(uniqueRatings, Collections.reverseOrder());

        double nthHighestRating = uniqueRatings.get(nthRank - 1);

        System.out.println("Users with the " + nthRank + " highest average rating (" + nthHighestRating + "):");

        for (UserData user : userDataList) {
            if (user.getAverageRating() == nthHighestRating) {
                System.out.println("User ID: " + user.getUserID() + ", Average Rating: " + String.format("%.3f", nthHighestRating));
            }
        }
    }

}
