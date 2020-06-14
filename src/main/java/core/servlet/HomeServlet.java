package core.servlet;

import base.face.connector.Request;
import base.face.connector.Response;
import base.face.session.Session;

public class HomeServlet implements Servlet {
    @Override
    public void init() {

    }

    @Override
    public void service(Request request, Response response) {
        Session session = request.getSession();
        String name = (String) session.getAttribute("name");
        String pwd = (String) session.getAttribute("pwd");
        if (name != null && name.equals("king") && pwd != null && pwd.equals("123")) {
            response.forword("index.html");
            response.responseStatic();
        } else {
            response.forword("login.html");
            response.responseStatic();
        }
    }

    @Override
    public void destroy() {

    }
}
