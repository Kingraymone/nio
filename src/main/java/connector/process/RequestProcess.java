package connector.process;

import connector.httpserver.Request;
import connector.httpserver.Response;
import core.process.StaticProcess;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

public class RequestProcess implements Runnable {
    private SocketChannel sc;

    public RequestProcess(SelectableChannel sc) {
        this.sc = (SocketChannel) sc;
    }

    @Override
    public void run() {
        Request request;
        Response response;
        try {
            request = new Request(sc);
            response = new Response(sc, request);
            request.parseHead(request.praseRequest());
            if (request.getUri().startsWith("/servlet")) {
                System.out.println("处理动态请求！");

            } else {
                System.out.println("处理静态请求！");
                StaticProcess staticProcess = new StaticProcess();
                staticProcess.process(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                sc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
