package repository;

import model.User;

import java.util.HashMap;

public class UserRepository {
    private final HashMap<String, User> userDB = new HashMap<>();

    public boolean save(String userId, User user) {
        userDB.put(userId, user);
        return true;
    }

    public boolean exists(String userId) {
        if (userDB.containsKey(userId)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUser(String id, String pw){
        if (!userDB.containsKey(id)){
            return false;
        }
        User u=userDB.get(id);
        if(!pw.equals(u.userPassword)){
            return false;
        }
        return true;
    }
}
