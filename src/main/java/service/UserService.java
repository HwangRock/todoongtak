package service;

import model.User;
import global.UserKey;
import repository.UserRepository;
import security.jwt.JwtBuilder;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final JwtBuilder jwtBuilder = new JwtBuilder();

    public boolean registerUser(User user) {
        UserKey key = new UserKey(user.userId, user.userName);
        if (!userRepository.exists(key) && userRepository.save(key, user)) {
            return true;
        } else {
            return false;
        }
    }

    public String createAccessToken(String userId) {
        String accessToken=jwtBuilder
                .userId(userId)
                .ttl(3600)
                .secret("dwdd")
                .build();

        return accessToken;
    }
}
