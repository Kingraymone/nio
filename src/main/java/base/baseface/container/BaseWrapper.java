package base.baseface.container;

import base.face.container.Wrapper;
import core.servlet.Servlet;

public class BaseWrapper extends BaseContainer implements Wrapper {
    // servlet实例
    Servlet instance = null;
    // servlet类名称
    String servletClass = null;

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public void allocate() {

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
