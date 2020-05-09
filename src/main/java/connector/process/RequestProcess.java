package connector.process;

import connector.httpserver.Request;
import connector.httpserver.Response;
import core.process.StaticProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class RequestProcess implements Runnable {
    private SelectionKey sk;

    public RequestProcess(SelectionKey sk) {
        this.sk = sk;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        Request request;
        logger.info("=========================================");
        try {
            logger.info("开始处理请求：{}", (((SocketChannel) sk.channel()).getRemoteAddress()));
            request = new Request((SocketChannel) sk.channel());
            String param = request.praseRequest();
            if (param != null) {
                request.parseHead(param);
            } else {
                sk.channel().close();
                logger.error("请求数据异常！关闭连接！");
                return;
            }
            Response response = new Response((SocketChannel) sk.channel(), request);
            if (request.getUri().startsWith("/servlet")) {
                logger.debug("开始处理动态请求！");
            } else {
                logger.debug("开始处理静态请求！");
                StaticProcess staticProcess = new StaticProcess();
                staticProcess.process(request, response);
            }
            logger.info("请求处理完毕，等待下次连接...........");
            sk.interestOps(SelectionKey.OP_READ);
        } catch (Exception e) {
            try {
                sk.channel().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            logger.error("解析uri异常！关闭连接！", e);
        } finally {
            logger.info("=========================================\n");
        }
    }
}
