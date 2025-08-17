package test;

public final class Perf {
    private Perf() {
    }


    public interface MeasuredTask {
        void run() throws Exception;
    }
}
