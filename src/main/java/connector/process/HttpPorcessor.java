package connector.process;

import connector.httpserver.Request;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;


/**
 * @Description 连接处理器，负责生成HttpRequest、HttpResponse并交付为core模块
 * @Package connector.process
 * @date 2020-04-14
 */
public class HttpPorcessor {
    //可读选择器
    private static  Selector selector;
    //第一次有请求时开启线程来处理selector中的可读事件
    private static volatile boolean run = false;
    //线程池处理请求
    private static ThreadPoolExecutor tpe;

    static{
        try {
            selector=Selector.open();
            tpe = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),200,60,TimeUnit.SECONDS,new LinkedBlockingQueue<>(), new ThreadPoolExecutor.AbortPolicy());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void process(SocketChannel sc){
        try {
            //非阻塞模式
            sc.configureBlocking(false);
            sc.register(selector,SelectionKey.OP_READ);
            if(!run){
                run = true;
                tpe.execute(new Runnable() {
                    @Override
                    public void run() {
                        while(true){
                            try {
                                int select = selector.select();
                                if(select<1){
                                    continue;
                                }
                                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                                while(iterator.hasNext()){
                                    SelectionKey key = iterator.next();
                                    iterator.remove();
                                    //可读
                                    if(key.isReadable()){
                                        tpe.execute(new RequestProcess(key.channel()));
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
