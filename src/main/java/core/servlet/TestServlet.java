package core.servlet;

import connector.httpserver.RequestFacade;
import connector.httpserver.ResponseFacade;

public class TestServlet implements Servlet {
    @Override
    public void init() {
        System.out.println("init()");
    }

    @Override
    public void service(RequestFacade request, ResponseFacade response) {

    }

    @Override
    public void destroy() {
        System.out.println("destroy()");
    }
}
