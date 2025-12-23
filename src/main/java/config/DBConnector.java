package config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = dotenv.get("DB_URL");
    private static final String user = dotenv.get("DB_USERNAME");
    private static final String password = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() {
        try {
            System.out.println("connect to DB: " + url);
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException("DB 연결 실패", e);
        }
    }
}
