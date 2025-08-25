package controller;

import global.Request;
import global.Route;

import java.io.PrintWriter;
import java.util.List;

public class HomeController implements Controller {

    public List<Route> routes() {
        return List.of(
                new Route("GET", "/", this)
        );
    }

    public void handle(Request req, PrintWriter res) {
        String body = """
                    <!doctype html>
                    <html lang="ko"><head><meta charset="utf-8"><title>SangNamJa</title></head>
                    <body>
                      <h1>투둥탁!</h1>
                      <ul>
                        <li><a href="/signup">회원가입</a></li>
                        <li><a href="/login">로그인</a></li>
                      </ul>
                    </body></html>
                """;
        byte[] bytes = body.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        res.println("HTTP/1.1 200 OK");
        res.println("Content-Type: text/html; charset=UTF-8");
        res.println("Content-Length: " + bytes.length);
        res.println();
        res.print(body);
        res.flush();
    }
}
