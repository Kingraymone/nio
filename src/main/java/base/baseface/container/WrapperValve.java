package base.baseface.container;

import base.face.connector.Request;
import base.face.connector.Response;
import core.servlet.Servlet;

/**
 * 基础Wrapper阀，用于执行servlet的service服务
 */
public class WrapperValve extends BaseValve {
    @Override
    public void invoke(Request request, Response response) {
        BaseWrapper container = (BaseWrapper) this.getContainer();
        Servlet servlet = container.allocate();
        servlet.service(request, response);
    }
}
