package controller;

import global.Request;
import global.Route;
import view.UserView;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LoginController implements Controller {

    public List<Route> routes() {
        return List.of(
                new Route("GET", "/login", this)
        );
    }

    public void handle(Request req, PrintWriter writer) {
        if (req.method.equals("GET")) {
            String html = UserView.loginForm();
            sendHtml(writer, html);
            return;
        }
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
