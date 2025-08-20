import global.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(1220);
        server.start();
    }
}
