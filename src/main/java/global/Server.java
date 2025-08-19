package global;

import controller.FrontController;

import java.io.*;
import java.net.*;

public class Server {
    private final int port;
    private final FrontController frontController = new FrontController();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        long startTime = System.currentTimeMillis();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            long endTime = System.currentTimeMillis();
            System.out.println("src.main.java.global.Server started time: " + (endTime - startTime) + "ms");
            System.out.println("Hello handsome HwangRock~! your server : http://localhost:" + port);

            while (true) {
                final Socket socket = serverSocket.accept();

                new Thread(() -> {
                    try {
                        socket.setSoTimeout(10_000);
                        frontController.dispatch(socket);
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            OutputStream out = socket.getOutputStream();
                            PrintWriter w = new PrintWriter(out, true);
                            w.println("HTTP/1.1 500 Internal src.main.java.global.Server Error");
                            w.println("Content-Type: text/plain; charset=UTF-8");
                            w.println("Content-Length: 21");
                            w.println();
                            w.print("Internal src.main.java.global.Server Error");
                            w.flush();
                        } catch (IOException ignored) {
                        }
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException ignored) {
                        }
                    }
                }).start();
            }
        }

    }
}
