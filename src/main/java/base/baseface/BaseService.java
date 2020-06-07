package base.baseface;

import base.face.connector.Connector;
import base.face.Lifecycle;
import base.face.Server;
import base.face.Service;
import base.face.container.Container;

import java.util.Arrays;

public class BaseService extends BaseLifecycle implements Service {
    // 服务器
    private Server server = null;
    // servlet容器
    private Container container = null;
    // 连接器
    private Connector[] connectors = new Connector[0];
    // 添加连接器的锁
    private final Object lock = new Object();

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;

    }

    @Override
    public void addConnector(Connector connector) {
        synchronized (lock) {
            Connector[] result = new Connector[connectors.length + 1];
            System.arraycopy(connectors, 0, result, 0, connectors.length);
            result[connectors.length] = connector;
            connectors = result;
        }
    }

    @Override
    public void removeConnector(Connector connector) {

    }

    @Override
    public Connector[] findConnectors() {
        return connectors;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public void init() {
        logger.debug("init()执行--Service！");
    }

    @Override
    public void start() {
        logger.debug("start()执行--Service！");
        Arrays.stream(connectors).forEach(x -> ((Lifecycle) x).start());
    }

    @Override
    public void stop() {
        logger.debug("stop()执行--Service！");
    }
}
