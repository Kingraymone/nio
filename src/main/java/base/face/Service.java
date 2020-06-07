package base.face;


import base.face.connector.Connector;
import base.face.container.Container;

/**
 * 服务器中对应的服务
 * 持有连接器和servlet容器
 * 启动连接器
 */
public interface Service {
    Container getContainer();

    void setContainer(Container container);

    void addConnector(Connector connector);

    void removeConnector(Connector connector);

    Connector[] findConnectors();

    void setServer(Server server);

    Server getServer();
}
