package controller;

import java.io.*;

public interface Controller {
    boolean supports(String method, String path);

    void handle(Request req, PrintWriter res) throws IOException;
}
