package service;

import model.User;
import repository.UserRepository;
import security.jwt.JwtBuilder;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final JwtBuilder jwtBuilder = new JwtBuilder();

    public boolean registerUser(User user) {
        String userId = user.userId;
        if (!userRepository.exists(userId) && userRepository.save(userId, user)) {
            return true;
        } else {
            return false;
        }
    }

    public String createAccessToken(String id,String pw) {
        if(!userRepository.checkUser(id,pw)){
            return "x";
        }
        String accessToken = jwtBuilder
                .userId(id)
                .ttl(3600)
                .secret("dwdd")
                .build();

        return accessToken;
    }
}
