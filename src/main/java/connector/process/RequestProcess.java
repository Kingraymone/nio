package connector.process;

import connector.httpserver.Request;
import connector.httpserver.Response;
import core.process.StaticProcess;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class RequestProcess implements Runnable {
    private SelectionKey sk;

    public RequestProcess(SelectionKey sk) {
        this.sk = sk;
    }

    @Override
    public void run() {
        Request request;
        try {
            System.out.println("数据读取交互开始！"+new Date());
            request = new Request((SocketChannel) sk.channel());
            String param = request.praseRequest();
            System.out.println("数据读取交互结束！");
            if (param != null) {
                request.parseHead(param);
            } else {
                sk.channel().close();
                System.out.println("请求数据异常！关闭连接！");
                return;
            }
            Response response = new Response((SocketChannel) sk.channel(), request);
            if (request.getUri().startsWith("/servlet")) {
                System.out.println("处理动态请求！");

            } else {
                System.out.println("处理静态请求！");
                StaticProcess staticProcess = new StaticProcess();
                staticProcess.process(request, response);
            }
            System.out.println("请求处理完毕，等待下次连接...........");
            sk.interestOps(SelectionKey.OP_READ);
        } catch (Exception e) {
            try {
                sk.channel().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("解析头部异常！关闭连接！");
            e.printStackTrace();
        }
    }
}
