package core.servlet;

import base.face.connector.Request;
import base.face.connector.Response;

public interface Servlet {
    void init();

    void service(Request request, Response response);

    void destroy();
}
