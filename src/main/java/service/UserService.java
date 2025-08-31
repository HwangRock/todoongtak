package service;

import model.User;
import repository.UserRepository;
import security.jwt.JwtBuilder;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final JwtBuilder jwtBuilder = new JwtBuilder();

    private final int TTL = 3600;

    String jwtSecret = System.getenv("JWT_KEY");

    public boolean registerUser(User user) {
        String userId = user.userId;
        if (!userRepository.exists(userId) && userRepository.save(userId, user)) {
            return true;
        } else {
            return false;
        }
    }

    public Optional<String> createAccessToken(String id, String pw) {
        if (!userRepository.checkUser(id, pw)) {
            return null;
        }
        String accessToken = jwtBuilder
                .userId(id)
                .ttl(TTL)
                .secret(jwtSecret)
                .build();

        return accessToken.describeConstable();
    }
}
