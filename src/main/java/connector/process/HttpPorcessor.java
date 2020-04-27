package connector.process;

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

    static {
        try {
            selector = Selector.open();
            tpe = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2, 20, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new ThreadPoolExecutor.AbortPolicy());
            System.out.println("初始线程池或选择器！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void process(SocketChannel sc) {
        try {
            //非阻塞模式
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_READ);
            if (!run) {
                System.out.println("开启线程等待读事件!");
                run = true;
                tpe.execute(() -> {
                    while (true) {
                        try {
                            int select = selector.select();
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
                                    RequestProcess requestProcess = new RequestProcess(key);
                                    System.out.println("开始处理请求！" + (((SocketChannel) key.channel()).getRemoteAddress()));
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
