package base.face;

/**
 * 服务器
 * 一个tomcat只有一个服务器
 * 通过server启动tomcat中所有容器
 * 可持有多个service
 */
public interface Server {
    void addService(Service service);

    void removeService(Service service);

    Service[] findServices();
}
