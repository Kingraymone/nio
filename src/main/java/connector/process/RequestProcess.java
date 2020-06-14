package connector.process;

import base.baseface.container.BaseContainer;
import base.face.connector.Connector;
import connector.httpserver.HttpRequest;
import connector.httpserver.HttpResponse;
import core.process.StaticProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class RequestProcess implements Runnable {
    private SelectionKey sk;
    private Connector connector;

    public RequestProcess(SelectionKey sk, Connector connector) {
        this.connector = connector;
        this.sk = sk;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        HttpRequest httpRequest;
        logger.info("=========================================");
        try {
            logger.info("开始处理请求：{}", (((SocketChannel) sk.channel()).getRemoteAddress()));
            httpRequest = new HttpRequest((SocketChannel) sk.channel());
            String param = httpRequest.praseRequest();
            if (param != null) {
                httpRequest.parse(param);
            } else {
                sk.channel().close();
                logger.error("请求数据异常！关闭连接！");
                return;
            }
            HttpResponse httpResponse = new HttpResponse((SocketChannel) sk.channel(), httpRequest);
            if (httpRequest.getUri().startsWith("/servlet") || httpRequest.getUri().startsWith("/home")) {
                logger.debug("开始处理动态请求！");
                ((BaseContainer) this.connector.getContainer()).invoke(httpRequest, httpResponse);
            } else {
                logger.debug("开始处理静态请求！");
                StaticProcess staticProcess = new StaticProcess();
                staticProcess.process(httpRequest, httpResponse);
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
