package connector.process;

import connector.httpserver.Request;
import connector.httpserver.Response;
import core.process.StaticProcess;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class RequestProcess implements Runnable {
    private SelectionKey sk;

    public RequestProcess(SelectionKey sk) {
        this.sk = sk;
    }

    @Override
    public void run() {
        Request request;
        try {
            request = new Request((SocketChannel)sk.channel());
            String param = request.praseRequest();
            if (param != null) {
                request.parseHead(param);
            }else{
                sk.cancel();
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
            sk.cancel();
            //响应结束修改为可读事件
            //sk.interestOps(SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
