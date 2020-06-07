package base.baseface;

import base.face.Lifecycle;
import base.face.Server;
import base.face.Service;

import java.util.Arrays;

public class BaseServer extends BaseLifecycle implements Server {
    // 关闭server端口
    private int port = 8001;
    // 是否满足stop条件
    private volatile boolean stopAwait = false;
    // Service数组
    private Service[] services = new Service[0];
    // 添加service锁
    private final Object lock = new Object();
    // 判断是否启动
    private boolean started = false;

    public void addService(Service service) {
        synchronized (lock) {
            Service[] newServices = new Service[services.length + 1];
            System.arraycopy(services, 0, newServices, 0, services.length);
            newServices[services.length] = service;
            services = newServices;
        }
    }

    public void removeService(Service service) {

    }

    public Service[] findServices() {
        return services;
    }

    @Override
    public void init() {
        logger.debug("init()执行--Server！");
    }

    @Override
    public void start() {
        logger.debug("start()执行--Server！");
        Arrays.stream(services).forEach(x -> ((Lifecycle) x).start());
    }

    @Override
    public void stop() {
        logger.debug("stop()执行--Server！");
        Arrays.stream(services).forEach(x -> ((Lifecycle) x).stop());
    }
}
