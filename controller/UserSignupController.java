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

public class UserSignupController implements Controller {
    private final UserService userService = new UserService();

    public List<Route> routes() {
        return List.of(
                new Route("POST", "/signup", this),
                new Route("GET", "/signup", this)
        );
    }

    public void handle(Request req, PrintWriter writer) throws IOException {
        if (req.method.equals("GET")) {
            String html = UserView.signupForm();
            sendHtml(writer, html);
            return;
        }
        Map<String, String> form = parseForm(req.body);
        String name = form.get("username");
        String id = form.get("userid");
        String pw = form.get("password");
        boolean ok = userService.registerUser(new User(name, id, pw));
        sendHtml(writer, ok ? UserView.successPage(name) : UserView.alreadyExistsPage());
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
