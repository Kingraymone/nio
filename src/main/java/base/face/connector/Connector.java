package base.face.connector;

import base.face.Service;
import base.face.container.Container;

/**
 * 连接器
 * 一个web服务器可以有多个连接器
 * 监听端口，获得连接，新建request和response，解析request
 * 持有servlet容器，调用invoke方法传递请求
 */
public interface Connector {
    Container getContainer();

    void setContainer(Container container);

    Service getService();

    void setService(Service service);
}
