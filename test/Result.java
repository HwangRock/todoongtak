package test;

public class Result {
    public final double avgMs, p50Ms, p95Ms, p99Ms;
    public final long allocPerOpBytes;

    public Result(double avgMs, double p50Ms, double p95Ms, double p99Ms, long allocPerOpBytes) {
        this.avgMs = avgMs;
        this.p50Ms = p50Ms;
        this.p95Ms = p95Ms;
        this.p99Ms = p99Ms;
        this.allocPerOpBytes = allocPerOpBytes;
    }

    @Override
    public String toString() {
        return String.format("avg=%.3fms p50=%.3fms p95=%.3fms p99=%.3fms alloc/op=%d",
                avgMs, p50Ms, p95Ms, p99Ms, allocPerOpBytes);
    }
}
