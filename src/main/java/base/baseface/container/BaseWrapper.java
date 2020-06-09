package base.baseface.container;

import base.face.connector.Request;
import base.face.connector.Response;
import base.face.container.Wrapper;
import core.servlet.Servlet;

public class BaseWrapper extends BaseContainer implements Wrapper {
    // servlet实例
    Servlet instance = null;
    // servlet类名称
    String servletClass = null;

    public void invoke(Request request, Response response){

    }
    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public Servlet allocate() {
        return null;
    }

    @Override
    public Servlet getServlet() {
        return instance;
    }

    @Override
    public String getServletClass() {
        return servletClass;
    }

    @Override
    public void setServletClass(String name) {
        this.servletClass = name;
    }
}
