package global;

import controller.Controller;

public final class Route {
    public final String method;
    public final String path;
    public final Controller controller;

    public Route(String method, String path, Controller controller) {
        this.method = method;
        this.path = path;
        this.controller = controller;
    }


}
