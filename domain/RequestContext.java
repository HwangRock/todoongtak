package domain;

public class RequestContext {
    public final String method;
    public final String path;
    public final long startNanos;
    public volatile long endNanos;
    public volatile int status = 200;
    public volatile long bytesWritten = 0;

    public RequestContext(String method, String path) {
        this.method = method;
        this.path = path;
        this.startNanos = System.nanoTime();
    }

    public long latencyNanos() {
        return endNanos == 0 ? 0 : endNanos - startNanos;
    }
}