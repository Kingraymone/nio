package base.baseface.container;

import base.baseface.BaseLifecycle;
import base.baseface.container.valve.BasePipeline;
import base.face.connector.Request;
import base.face.connector.Response;
import base.face.container.Container;
import base.face.container.Pipeline;

import java.util.HashMap;
import java.util.Map;

public class BaseContainer extends BaseLifecycle implements Container {
    // 管道流
    private Pipeline pipeline = new BasePipeline(this);
    // 父容器
    private Container parent = null;
    // 子容器
    private Map<String, Container> children = new HashMap<>(8);
    // 子容器锁
    private final Object lock = new Object();
    // 容器名称
    private String name;

    /**
     * 连接器处理完毕有调用的方法
     *
     * @param request
     * @param response
     */
    public void invoke(Request request, Response response) {
        // start
        start();
        // 调用容器本身valve
        if (pipeline.getFirst() == null) {
            pipeline.getBasic().invoke(request, response);
        } else {
            pipeline.getFirst().invoke(request, response);
        }
    }

    @Override
    public Pipeline getPipeline() {
        return pipeline;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setParent(Container parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Container container) {
        synchronized (lock) {
            children.put(container.getName(), container);
        }
    }

    @Override
    public Container findChild(Container container) {
        return children.get(container.getName());
    }

    @Override
    public Container findChildren(String name) {
        return children.get(name);
    }

    @Override
    public Map getChildren() {
        return children;
    }

    @Override
    public void removeChild() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void init() {
        logger.debug("init()执行--Container");
    }

    @Override
    public void start() {
        logger.debug("start()执行--Container！");
    }

    @Override
    public void stop() {
        logger.debug("stop()执行--Container！");
    }
}
