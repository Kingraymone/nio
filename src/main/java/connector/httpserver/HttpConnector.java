package connector.httpserver;

import connector.process.HttpPorcessor;
import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Description 连接器，负责处理连接请求，并将连接交付为HttpProcessor处理
 * @Package connector.httpserver
 * @date 2020-04-14
 */
@Data
public class HttpConnector implements Runnable{
    private boolean shutdown = false;

    @Override
    public void run() {
        try {
            //根据操作系统获得对应选择器
            Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //设置通道为非阻塞模式
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(8090));
            //注册感兴趣的事件
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                //阻塞等待事件就绪
                int ready = selector.select();
                if (ready < 1) {
                    continue;
                }
                //存在就绪事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    //处理事件后删除，避免重复出现
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        // 有新连接就绪
                        SocketChannel accept = ssc.accept();
                        System.out.println("新连接到来：" + accept.getLocalAddress());
                        // 将新连接交付为HTTPProcessor处理
                        HttpPorcessor hp = new HttpPorcessor();
                        hp.process(accept);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1,1,60, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),new ThreadPoolExecutor.AbortPolicy());
        tpe.execute(this);
    }
    public void start1() {
        try {
            //根据操作系统获得对应选择器
            Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            //设置通道为非阻塞模式
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(8090));
            //注册感兴趣的事件
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                //阻塞等待事件就绪
                int ready = selector.select();
                if (ready < 1) {
                    continue;
                }
                //存在就绪事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    //处理事件后删除，避免重复出现
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        // 有新连接就绪
                        SocketChannel accept = ssc.accept();
                        System.out.println("新连接到来：" + accept.getLocalAddress());
                        // 将新连接交付为HTTPProcessor处理
                    } /*else if (selectionKey.isReadable()) {
                        // 可读事件就绪,通过线程池解析请求
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        System.out.println("开始解析请求数据！" + channel);
                        Request request = new Request(channel);
                        request.parse();
                        selectionKey.attach(request);
                        System.out.println("解析uri结果为：" + request);
                        // 修改感兴趣事件为写
                        selectionKey.interestOps(SelectionKey.OP_WRITE);
                    } else if (selectionKey.isWritable()) {
                        // 可写事件就绪，通过连接池处理响应
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        pool.execute(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("开始发送响应内容！");
                                Response response = new Response(channel);
                                response.setRequest((Request) selectionKey.attachment());
                                response.responseResult();
                            }
                        });
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }*/
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
