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

public class UserSignupController implements Controller {
    private final UserService userService = new UserService();

    public boolean supports(String method, String path) {
        if ((method.equals("POST") || method.equals("GET")) && path.equals("/signup")) {
            return true;
        } else {
            return false;
        }
    }

    public void handle(BufferedReader reader, PrintWriter writer) throws IOException {
        String methodLine = reader.readLine();
        String method = methodLine.split(" ")[0];
        String path = methodLine.split(" ")[1];
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
        }
        if (method == "GET") {
            String res = UserView.signupForm();
            sendHtml(writer, res);
        } else {
            StringBuilder bodyBuilder = new StringBuilder();
            while (reader.ready()) {
                bodyBuilder.append((char) reader.read());
            }
            String body = bodyBuilder.toString().trim();
            System.out.println("RAW body: " + body);

            Map<String, String> formData = parseForm(body);
            String userName = formData.get("username");
            String userId = formData.get("userid");
            String userPw = formData.get("password");

            User user = new User(userName, userId, userPw);
            boolean success = userService.registerUser(user);
            if (success) {
                sendHtml(writer, UserView.successPage(userName));
            } else {
                sendHtml(writer, UserView.alreadyExistsPage());
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
