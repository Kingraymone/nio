package connector.httpserver;

import base.baseface.connector.BaseConnector;
import connector.process.HttpPorcessor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;


/**
 * @Description 连接器，负责处理连接请求，并将连接交付为HttpProcessor处理
 * @Package connector.httpserver
 * @date 2020-04-14
 */
@Data
public class HttpConnector extends BaseConnector implements Runnable {
    private boolean shutdown = false;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
                        logger.info("新连接到来：{}", accept.getRemoteAddress());
                        // 将新连接交付为HTTPProcessor处理
                        HttpPorcessor.process(accept,this);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("监听出错！关闭连接！", e);
        }
    }

    public void start() {
        logger.debug("start()执行--Connector！");
        ExecutorService tpe = Executors.newFixedThreadPool(1);
        tpe.execute(this);
    }

    @Override
    public void stop() {
        logger.debug("stop()执行--Connector！");
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
                        System.out.println("新连接到来：" + accept.getRemoteAddress());
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
