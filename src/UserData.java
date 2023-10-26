public class UserData {
    private int userID;
    private int source;
    private int target;
    private int rating;
    private double averageRating;

    public UserData(int userID, int source, int target, int rating) {
        this.userID = userID;
        this.source = source;
        this.target = target;
        this.rating = rating;
        this.averageRating = getAverageRating(); // Initialize averageRating to 0.0
    }

    // Getter and setter for averageRating
    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getUserID() {
        return userID;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "UserID: " + userID + ", Source: " + source + ", Target: " + target + ", Rating: " + rating + ", Average Rating: " + averageRating;
    }


}
