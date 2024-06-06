package entity;

public class User {
    private final String userID;
    private String role;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String[] checkedOutBooks;

    public User(String userID, String role, String username, String password, String firstName, String lastName, String[] checkedOutBooks) {
        this.userID = userID;
        this.role = role;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.checkedOutBooks = checkedOutBooks;
    }

    public String getUserID() {
        return userID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String[] getCheckedOutBooks() {
        return checkedOutBooks;
    }

    public void setCheckedOutBooks(String[] checkedOutBooks) {
        this.checkedOutBooks = checkedOutBooks;
    }
}
