package base.face.container;

import core.servlet.Servlet;

/**
 * 修饰servlet类
 */
public interface Wrapper {
    void load();

    void unload();

    void allocate();

    Servlet getServlet();

    String getServletClass();

    void setServletClass(String name);
}
