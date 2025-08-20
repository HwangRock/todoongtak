package global;

import java.util.Map;

public class Request {
    public final String method;
    public final String path;
    public final String httpVersion;
    public final Map<String,String> headers;
    public final String body;

    public Request(String method, String path, String httpVersion,
                   Map<String,String> headers, String body) {
        this.method = method;
        this.path = path;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;
    }

    public String header(String name){
        return headers.getOrDefault(name.toLowerCase(), null);
    }
}
