package test;

import bench.RegisterUserPerf;

public class TestServer {
    public static void main(String[] args) throws Exception {
        var t = new RegisterUserPerf();
        t.report();
    }
}
