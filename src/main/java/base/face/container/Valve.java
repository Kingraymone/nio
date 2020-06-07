package base.face.container;

import base.face.connector.Request;
import base.face.connector.Response;

public interface Valve {
    Valve getNext();
    void setNext(Valve valve);
    // 责任链调用
    void invoke(Request request, Response response);
    // 用于重载
    void backgroundProcess();
}
