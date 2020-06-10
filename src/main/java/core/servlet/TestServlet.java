package core.servlet;

import base.face.connector.Request;
import base.face.connector.Response;
import connector.httpserver.RequestFacade;
import connector.httpserver.ResponseFacade;

public class TestServlet implements Servlet {
    @Override
    public void init() {
        System.out.println("init()");
    }

    @Override
    public void service(Request request, Response response) {
        response.write("响应！abc");
    }

    @Override
    public void destroy() {
        System.out.println("destroy()");
    }
}
