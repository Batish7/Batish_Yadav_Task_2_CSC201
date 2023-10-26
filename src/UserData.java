public class UserData {
    private int userID;
    private double rating;
    private double averageRating;

    public UserData(int userID, double rating) {
        this.userID = userID;
        this.rating = rating;
        this.averageRating = 0.0; // Initialize averageRating to 0.0
    }

    public int getUserID() {
        return userID;
    }

    public double getRating() {
        return rating;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "User_ID: " + userID + ", Rating: " + rating + ", Average Rating: " + averageRating;
    }
}
