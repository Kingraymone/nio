package core.servlet;

import base.face.connector.Request;
import base.face.connector.Response;
import base.face.session.Session;

public class LogoutServlet implements Servlet {
    @Override
    public void init() {

    }

    @Override
    public void service(Request request, Response response) {
        Session session = request.getSession();
        session.removeAttribute("name");
        session.removeAttribute("pwd");
        // 请求转发
        response.forword("login.html");
        response.responseStatic();
    }

    @Override
    public void destroy() {

    }
}
