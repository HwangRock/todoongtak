package controller;

import global.ApiKey;
import global.Request;
import global.Route;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontController {

    private final List<Controller> controllers = new ArrayList<>();
    private final HashMap<ApiKey, Controller> controllerHashMap = new HashMap<>();

    private void InitializeHashMap() {
        for (Controller c : controllers) {
            for (Route r : c.routes()) {
                ApiKey key = new ApiKey(r.method, r.path);
                if (controllerHashMap.containsKey(key)) {
                    throw new IllegalStateException("Duplicate route: " + key);
                }
                controllerHashMap.put(key, c);
            }
        }
    }

    public FrontController() {
        controllers.add(new UserSignupController());
        controllers.add(new HomeController());
        InitializeHashMap();
    }

    public void dispatch(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        String start = reader.readLine();
        if (start == null || start.isBlank()) {
            return;
        }
        String[] first = start.split(" ", 3);
        String method = first[0];
        String path = first[1];
        String httpVer = first.length > 2 ? first[2] : "HTTP/1.1";

        Map<String, String> headers = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int idx = line.indexOf(':');
            if (idx > 0) {
                String k = line.substring(0, idx).trim().toLowerCase();
                String v = line.substring(idx + 1).trim();
                headers.put(k, v);
            }
        }

        String body = "";
        String cl = headers.get("content-length");
        if (cl != null) {
            int len = Integer.parseInt(cl);
            char[] buf = new char[len];
            int read = 0;
            while (read < len) {
                int r = reader.read(buf, read, len - read);
                if (r == -1) break;
                read += r;
            }
            body = new String(buf, 0, read);
        }

        ApiKey reqKey = new ApiKey(method, path);
        Request req = new Request(method, path, httpVer, headers, body);

        Controller controller = controllerHashMap.get(reqKey);
        if (controller != null) {
            controller.handle(req, writer);
            return;
        }

        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println("Content-Length: 22");
        writer.println();
        writer.println("<h1>404 Not Found</h1>");
        writer.flush();
    }

}
