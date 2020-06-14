package core.servlet;

import base.face.connector.Request;
import base.face.connector.Response;
import base.face.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class LoginServlet implements Servlet {
    Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void init() {

    }

    @Override
    public void service(Request request, Response response) {
        // 获得用户名和密码
        HashMap<String, String> params = request.getParams();
        if (params.size() == 0) {
            response.write("请求参数为空！<a href='/login.html'>请重新登录</a>");
        }
        String username = params.get("username");
        String password = params.get("password");
        logger.info("请求用户名：" + username + "  密码：" + password);
        if (username.equals("king") && password.equals("123")) {
            // 如果session为空设置
            Session session = request.getSession();
            if (session.getAttribute("name") == null) {
                logger.info("生成SessionId：" + session.getId());
                session.setAttribute("name", username);
                session.setAttribute("pwd", password);
                response.getHead().put("Set-Cookie", "kingSession=" + session.getId() + ";path=/");
            }
            response.forword("index.html");
            response.responseStatic();
        } else {
            response.write("用户名密码错误！<a href='/login.html'>请重新登录</a>");
        }
    }

    @Override
    public void destroy() {

    }
}
