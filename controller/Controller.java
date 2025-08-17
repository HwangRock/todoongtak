package controller;

import global.Request;
import global.Route;

import java.io.*;
import java.util.List;

public interface Controller {

    void handle(Request req, PrintWriter res) throws IOException;

    default List<Route> routes() {
        return List.of();
    }
}
