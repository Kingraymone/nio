package core.servlet;

import connector.httpserver.RequestFacade;
import connector.httpserver.ResponseFacade;

public interface Servlet {
    void init();

    void service(RequestFacade request,ResponseFacade response);

    void destroy();
}
