package connector.process;

import connector.httpserver.HttpRequest;
import connector.httpserver.HttpResponse;
import core.process.StaticProcess;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ResponseProcess implements Runnable {
    private SelectionKey sk;

    public ResponseProcess(SelectionKey sk) {
        this.sk = sk;
    }

    @Override
    public void run() {
        HttpRequest httpRequest = (HttpRequest) sk.attachment();
        try {
            if (httpRequest != null) {
                HttpResponse httpResponse = new HttpResponse((SocketChannel) sk.channel(), httpRequest);
                if (httpRequest.getUri().startsWith("/servlet")) {
                    System.out.println("处理动态请求！");

                } else {
                    System.out.println("处理静态请求！");
                    StaticProcess staticProcess = new StaticProcess();
                    staticProcess.process(httpRequest, httpResponse);
                }
                sk.channel();
                //响应结束修改为可读事件
                //sk.interestOps(SelectionKey.OP_READ);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
