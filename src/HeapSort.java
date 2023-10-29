import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HeapSort {
    public static void main(String[] args) {
        String inputFile = "D:\\2nd Year sem 1\\ICT 221\\Task_2_CSC201\\RatingResults.csv";  // Replace with your CSV file path
        String outputFile = "HeapSort.csv";  // Replace with the desired output file path

        // Read the CSV file and store user data in an ArrayList
        ArrayList<UserData> userDataList = readCSVFile(inputFile);

        // Calculate and set the average rating for each user
        calculateAverageRatings(userDataList);

        // Sort the user data using Heap Sort based on Average Rating
        heapSort(userDataList);

        // Write the sorted data back to a new CSV file
        writeCSVFile(outputFile, userDataList);
        printUsersWithNthHighestAverageRating(7,userDataList);
    }

    public static void heapSort(ArrayList<UserData> userDataList) {
        int n = userDataList.size();

        // Build a max-heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(userDataList, n, i);
        }

        // Extract elements one by one from the heap
        for (int i = n - 1; i >= 0; i--) {
            // Move the current root to the end
            Collections.swap(userDataList, 0, i);

            // Call max heapify on the reduced heap
            heapify(userDataList, i, 0);
        }
    }

    public static void heapify(ArrayList<UserData> userDataList, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && userDataList.get(left).getAverageRating() > userDataList.get(largest).getAverageRating()) {
            largest = left;
        }

        if (right < n && userDataList.get(right).getAverageRating() > userDataList.get(largest).getAverageRating()) {
            largest = right;
        }

        if (largest != i) {
            Collections.swap(userDataList, i, largest);
            heapify(userDataList, n, largest);
        }
    }
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
