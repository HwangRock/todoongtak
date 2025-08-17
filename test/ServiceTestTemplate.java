package test;

import java.io.IOException;

public abstract class ServiceTestTemplate<S> {
    protected abstract S service() throws IOException;

    protected abstract void act(S svc) throws IOException;

    protected int warmupIters() {
        return 50;
    }

    protected int measureIters() {
        return 300;
    }

    public Result testing() throws Exception {
        S svc = service();
        Result metric = Perf.measure(() -> act(svc), warmupIters(), measureIters());

        return metric;
    }

    public void report() throws Exception {
        Result metric = testing();
        String report = metric.toString();
        System.out.println(report);
    }
}
