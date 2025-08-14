package controller;

import java.io.*;
import java.util.List;

public interface Controller {
    boolean supports(String method, String path);

    void handle(Request req, PrintWriter res) throws IOException;

    default List<Route> routes() { return List.of(); }
}
