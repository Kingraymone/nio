package proxy;

public class Server implements Response,Log {
    @Override
    public void log(int i) {
        System.out.println("打印日志！"+i);
    }

    @Override
    public int test(int i, String k) {
        System.out.println("多参数测试"+i+k);
        return 0;
    }

    @Override
    public void response() {
        System.out.println("发送响应！");
    }
}
