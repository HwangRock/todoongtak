package controller;

import domain.Request;

import java.io.PrintWriter;

public class HomeController implements Controller {

    public boolean supports(String method, String path) {
        if (method.equals("GET") && path.equals("/")) {
            return true;
        } else {
            return false;
        }
    }

    public void handle(Request req, PrintWriter res) {
        String body = """
                    <!doctype html>
                    <html lang="ko"><head><meta charset="utf-8"><title>SangNamJa</title></head>
                    <body>
                      <h1>투둥탁!</h1>
                      <ul>
                        <li><a href="/signup">회원가입</a></li>
                        <li><a href="/login">로그인(예정)</a></li>
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
