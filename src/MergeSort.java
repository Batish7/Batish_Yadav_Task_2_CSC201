import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class MergeSort {
    public static void main(String[] args) {
        String inputFile = "D:\\2nd Year sem 1\\ICT 221\\Task_2_CSC201\\RatingResults.csv";  // Replace with your CSV file path
        String outputFile = "MergeSort.csv";  // Replace with the desired output file path

        // Read the CSV file and store user data in an ArrayList
        ArrayList<UserData> userDataList = readCSVFile(inputFile);

        // Calculate and set the average rating for each user
        calculateAverageRatings(userDataList);

        // Sort the user data using Merge Sort based on Average Rating
        mergeSort(userDataList);

        // Write the sorted data back to a new CSV file
        writeCSVFile(outputFile, userDataList);

        // Call the method to print users with the 4th highest average rating (7.5)
        printUsersWithNthHighestAverageRating(7, userDataList);



}

    public static void mergeSort(ArrayList<UserData> userDataList) {
        if (userDataList.size() <= 1) {
            return;
        }

        int mid = userDataList.size() / 2;
        ArrayList<UserData> left = new ArrayList<>(userDataList.subList(0, mid));
        ArrayList<UserData> right = new ArrayList<>(userDataList.subList(mid, userDataList.size()));

        mergeSort(left);
        mergeSort(right);

        merge(userDataList, left, right);
    }

    public static void merge(ArrayList<UserData> userDataList, ArrayList<UserData> left, ArrayList<UserData> right) {
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

    // Read the CSV file and store user data in an ArrayList
    public static ArrayList<UserData> readCSVFile(String filePath) {
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