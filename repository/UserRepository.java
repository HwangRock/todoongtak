package repository;

import model.User;

import java.util.HashMap;

public class UserRepository {
    private final HashMap<UserKey, User> userDB = new HashMap<>();

    public boolean save(User user) {
        UserKey key = new UserKey(user.userId, user.userName);
        if (!userDB.containsKey(key)) {
            userDB.put(key, user);
            return true;
        } else {
            return false;
        }
    }
}
