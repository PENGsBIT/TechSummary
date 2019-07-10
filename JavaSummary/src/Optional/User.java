package Optional;

public class User {
    String userName;
    Integer ID;

    public User(String userName, Integer ID) {
        this.userName = userName;
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public User getUserById(int id) {
        if (id == ID) {
            return this;
        }
       return null;
    }
}
