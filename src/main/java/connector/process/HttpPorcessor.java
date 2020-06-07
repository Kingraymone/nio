package connector.process;

import base.face.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Description 连接处理器，负责生成HttpRequest、HttpResponse并交付为core模块
 * @Package connector.process
 * @date 2020-04-14
 */
public class HttpPorcessor {
    //可读选择器
    private static Selector selector;
    //第一次有请求时开启线程来处理selector中的可读事件
    private static volatile boolean run = false;
    //线程池处理请求
    private static ThreadPoolExecutor tpe;
    private static Logger logger = LoggerFactory.getLogger(HttpPorcessor.class);
    static {
        try {
            selector = Selector.open();
            tpe = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*4, 40, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.AbortPolicy());
            logger.info("初始线程池和选择器！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void process(SocketChannel sc, Connector connector) {
        try {
            //非阻塞模式
            sc.configureBlocking(false);
            //selector.wakeup();
            sc.register(selector, SelectionKey.OP_READ);
            if (!run) {
                run = true;
                tpe.execute(() -> {
                    while (true) {
                        try {
                            int select = selector.select(10);
                            if (select < 1) {
                                continue;
                            }
                            Set<SelectionKey> selectionKeys = selector.selectedKeys();
                            Iterator<SelectionKey> iterator = selectionKeys.iterator();
                            while (iterator.hasNext()) {
                                //由于水平触发则会处于一直可读
                                SelectionKey key = iterator.next();
                                iterator.remove();
                                //可读
                                if (key.isValid() && key.isReadable()) {
                                    RequestProcess requestProcess = new RequestProcess(key,connector);
                                    //切换为写事件
                                    key.interestOps(SelectionKey.OP_WRITE);
                                    tpe.execute(requestProcess);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
