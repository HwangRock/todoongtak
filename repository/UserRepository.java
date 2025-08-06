package repository;

import model.User;

import java.util.HashMap;

public class UserRepository {
    private final HashMap<UserKey, User> userDB = new HashMap<>();

    public boolean save(UserKey key, User user) {
        userDB.put(key, user);
        return true;
    }

    public boolean exists(UserKey key) {
        if (userDB.containsKey(key)) {
            return true;
        } else {
            return false;
        }
    }
}
