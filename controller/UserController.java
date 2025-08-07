package controller;

import model.User;
import service.UserService;
import view.UserView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    private final UserService userService = new UserService();

    public void handleRequest(String method, String path, BufferedReader reader, PrintWriter writer) throws IOException {
        if (method.equals("GET") && path.equals("/signup")) {
            String view = UserView.signupForm();
            sendHtml(writer, view);
        } else if (method.equals("POST") && path.equals("/signup")) {

            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                // 헤더 스킵
            }

            // 바디 읽기
            StringBuilder bodyBuilder = new StringBuilder();
            while (reader.ready()) {
                bodyBuilder.append((char) reader.read());
            }
            String body = bodyBuilder.toString().trim();
            System.out.println("RAW body: " + body);

            Map<String, String> formData = parseForm(body);
            String name = formData.get("username");
            String id = formData.get("userid");
            String pw = formData.get("password");
            System.out.println("Parsed - name: " + name + ", id: " + id + ", pw: " + pw);

            User user = new User(name, id, pw);
            boolean success = userService.registerUser(user);

            if (success) {
                String successView = UserView.successPage(name);
                sendHtml(writer, successView);
            } else {
                String failView = UserView.alreadyExistsPage();
                sendHtml(writer, failView);
            }
        }

    }

    private Map<String, String> parseForm(String body) {
        Map<String, String> result = new HashMap<>();
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                result.put(kv[0], kv[1]);
            }
        }
        return result;
    }

    private void sendHtml(PrintWriter writer, String body) throws UnsupportedEncodingException {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println("Content-Length: " + body.getBytes("UTF-8").length);
        writer.println();
        writer.println(body);
        writer.flush();
    }
}
