import view.UserView;

import java.io.*;
import java.net.*;

public class Server {
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Hello handsome HwangRock~! your server : http://localhost:" + port);
        while (true) {
            Socket socket = serverSocket.accept();

            // 클라이언트와의 연결 수락
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            //서버 로깅용
            String requestLine = reader.readLine();
            System.out.println("요청: " + requestLine);

            //기본 응답
            //String responseBody = UserView.signupForm();
            String responseBody = "<h1>안녕하세요! 오늘도 잘생기셨군요.</h1>";

            // HTTP 응답 전송
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text/html; charset=UTF-8");
            writer.println("Content-Length: " + responseBody.getBytes().length);
            writer.println();
            writer.println(responseBody);

            socket.close();
        }
    }
}
