import bench.*;

public class TestServer {
    public static void main(String[] args) throws Exception {
        var t = new LoginUserPerf();
        t.report();
    }
}
