package service;

import model.User;
import repository.UserKey;
import repository.UserRepository;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public boolean registerUser(User user) {
        UserKey key = new UserKey(user.userId, user.userName);
        if (!userRepository.exists(key) && userRepository.save(key, user)) {
            return true;
        } else {
            return false;
        }
    }
}
