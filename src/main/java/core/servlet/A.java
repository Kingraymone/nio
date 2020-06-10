package core.servlet;

import base.face.connector.Request;
import base.face.connector.Response;

public class A implements Servlet {
    @Override
    public void init() {

    }

    @Override
    public void service(Request request, Response response) {
        response.write("A-servlet");
    }

    @Override
    public void destroy() {

    }
}
