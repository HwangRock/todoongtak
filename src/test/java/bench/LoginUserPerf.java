package bench;

import service.UserService;
import test.ServiceTestTemplate;

public class LoginUserPerf  extends ServiceTestTemplate<UserService> {
    @Override
    protected UserService service() {
        return new UserService();
    }

    @Override
    protected void act(UserService svc){
        String userId="hwang12345";
        String pw="a1235";
        String at=svc.createAccessToken(userId,pw);
        System.out.println(at);
    }
}
