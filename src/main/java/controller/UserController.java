package controller;

import global.Request;
import global.Route;
import model.User;
import service.UserService;
import view.UserView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController implements Controller {
    private final UserService userService = new UserService();

    public List<Route> routes() {
        return List.of(
                new Route("POST", "/signup", this),
                new Route("GET", "/signup", this),
                new Route("GET", "/login", this),
                new Route("POST", "/login", this)
        );
    }

    public void handle(Request req, PrintWriter writer) throws IOException {
        if (req.method.equals("GET") && req.path.equals("/signup")) {
            String html = UserView.signupForm();
            sendHtml(writer, html);
            return;
        }
        if (req.method.equals("POST") && req.path.equals("/signup")) {
            Map<String, String> form = parseForm(req.body);
            String name = form.get("username");
            String id = form.get("userid");
            String pw = form.get("password");
            boolean ok = userService.registerUser(new User(name, id, pw));
            sendHtml(writer, ok ? UserView.successPage(name) : UserView.alreadyExistsPage());
        }
        if (req.method.equals("GET") && req.path.equals("/login")) {
            String html = UserView.loginForm();
            sendHtml(writer, html);
            return;
        }
        if (req.method.equals("POST") && req.path.equals("/login")) {
            Map<String, String> form = parseForm(req.body);
            String id = form.get("userid");
            String pw = form.get("password");
            String jwt = userService.createAccessToken(id, pw);
            if (!jwt.equals("x")) {
                writer.println("HTTP/1.1 302 Found");
                writer.println("Set-Cookie: access_token=" + jwt
                        + "; Path=/; HttpOnly; SameSite=Lax; Max-Age=3600");
                writer.println("Location: /");
                writer.println("Content-Length: 0");
                writer.println();
                return;
            } else {
                String html = "<h1>로그인 실패</h1><a href=\"/login\">다시 시도</a>";
                sendHtml(writer, html);
                return;
            }
        }
    }

    private Map<String, String> parseForm(String body) {
        Map<String, String> m = new HashMap<>();
        for (String p : body.split("&")) {
            String[] kv = p.split("=", 2);
            if (kv.length == 2) {
                m.put(URLDecoder.decode(kv[0].trim(), StandardCharsets.UTF_8),
                        URLDecoder.decode(kv[1].trim(), StandardCharsets.UTF_8));
            }
        }
        return m;
    }


    private void sendHtml(PrintWriter w, String body) {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        w.println("HTTP/1.1 200 OK");
        w.println("Content-Type: text/html; charset=UTF-8");
        w.println("Content-Length: " + bytes.length);
        w.println();
        w.write(body);
        w.flush();
    }
}
