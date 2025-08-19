package bench;

import model.User;
import service.UserService;
import test.ServiceTestTemplate;

public class RegisterUserPerf extends ServiceTestTemplate<UserService> {
    private int seq = 0;

    @Override
    protected UserService service() {
        return new UserService();
    }

    @Override
    protected void act(UserService svc) {
        int i = seq++;
        User u = new User("rock" + i, "rock" + i, "pw1234");
        svc.registerUser(u);
    }
}
