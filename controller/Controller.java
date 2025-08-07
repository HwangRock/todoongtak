package controller;

import java.io.*;

public interface Controller {
    boolean supports(String method, String path);
    void handle(BufferedReader reader, PrintWriter writer) throws IOException;
}
