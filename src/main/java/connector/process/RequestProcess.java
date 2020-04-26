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
                return;
            }
            sk.attach(request);
            //请求处理完成修改感兴趣事件为读
            sk.interestOps(SelectionKey.OP_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
