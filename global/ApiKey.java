package global;

import java.util.Objects;

public class ApiKey {
    public String method;
    public String path;

    public ApiKey(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiKey)) return false;
        ApiKey apiKey = (ApiKey) o;
        return Objects.equals(method, apiKey.method) &&
                Objects.equals(path, apiKey.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
