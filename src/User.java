public class User {
    private String userId;
    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean authenticate(String inputUserId, String inputPassword) {
        return this.userId.equals(inputUserId) && this.password.equals(inputPassword);
    }
}
