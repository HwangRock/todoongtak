package test;

import com.sun.management.ThreadMXBean;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Perf {
    private Perf() {
    }


    public interface MeasuredTask {
        void run() throws Exception;
    }

    public static Result measure(MeasuredTask task, int warmupIters, int iters) throws Exception {
        ThreadMXBean tbean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
        boolean allocSupported = tbean.isThreadAllocatedMemorySupported();
        List<Long> nanos = new ArrayList<>(iters);
        List<Long> allocs = new ArrayList<>(iters);

        if (allocSupported && !tbean.isThreadAllocatedMemoryEnabled()) {
            tbean.setThreadAllocatedMemoryEnabled(true);
        }
        long tid = Thread.currentThread().getId();

        for (int i = 0; i < warmupIters; i++) {
            task.run();
        }

        for (int i = 0; i < iters; i++) {
            long beforeAlloc;
            if (allocSupported) {
                beforeAlloc = tbean.getThreadAllocatedBytes(tid);
            } else {
                beforeAlloc = 0L;
            }

            long start = System.nanoTime();
            task.run();
            long end = System.nanoTime();

            long afterAlloc;
            if (allocSupported) {
                afterAlloc = tbean.getThreadAllocatedBytes(tid);
            } else {
                afterAlloc = 0L;
            }

            nanos.add(end - start);
            if (allocSupported) allocs.add(Math.max(0L, afterAlloc - beforeAlloc));
        }

        Collections.sort(nanos);
        double avgMs = nanos.stream().mapToDouble(x -> x / 1_000_000.0).average().orElse(0);
        double p50Ms = percentile(nanos, 50) / 1_000_000.0;
        double p95Ms = percentile(nanos, 95) / 1_000_000.0;
        double p99Ms = percentile(nanos, 99) / 1_000_000.0;

        long allocPerOp = -1;
        if (!allocs.isEmpty()) {
            long sum = 0;
            for (long a : allocs) sum += a;
            allocPerOp = sum / allocs.size();
        }

        return new Result(avgMs, p50Ms, p95Ms, p99Ms, allocPerOp);
    }

    private static long percentile(List<Long> sortedNanos, int p) {
        if (sortedNanos.isEmpty()) {
            return 0L;
        }
        int idx = (int) Math.ceil(p / 100.0 * sortedNanos.size()) - 1;
        if (idx < 0) {
            idx = 0;
        }
        if (idx >= sortedNanos.size()) {
            idx = sortedNanos.size() - 1;
        }

        return sortedNanos.get(idx);
    }
}
