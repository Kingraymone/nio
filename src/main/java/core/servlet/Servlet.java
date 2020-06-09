package core.servlet;

import base.face.connector.Request;
import base.face.connector.Response;
import connector.httpserver.RequestFacade;
import connector.httpserver.ResponseFacade;

public interface Servlet {
    void init();

    void service(Request request, Response response);

    void destroy();
}
