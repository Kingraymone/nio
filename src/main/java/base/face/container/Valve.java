package base.face.container;

import base.face.connector.Request;
import base.face.connector.Response;

public interface Valve {
    // 获得当前阀所属容器
    Container getContainer();
    void setContainer(Container container);

    Valve getNext();
    void setNext(Valve valve);
    // 责任链调用
    void invoke(Request request, Response response);
    // 用于重载
    void backgroundProcess();
}
