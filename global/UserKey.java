package global;

public class UserKey {
    public String userId;
    public String userName;

    public UserKey(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserKey)) return false;
        UserKey other = (UserKey) o;
        return userId.equals(other.userId) && userName.equals(other.userName);
    }

    @Override
    public int hashCode() {
        return userId.hashCode() + userName.hashCode();
    }
}
