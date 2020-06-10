package base.baseface.container.valve;

import base.baseface.container.BaseWrapper;
import base.baseface.container.valve.BaseValve;
import base.face.connector.Request;
import base.face.connector.Response;
import core.servlet.Servlet;

/**
 * 基础Wrapper，调用servlet的service方法
 */
public class WrapperValve extends BaseValve {
    @Override
    public void invoke(Request request, Response response) {
        BaseWrapper wrapper = (BaseWrapper) this.getContainer();
        Servlet servlet = wrapper.allocate();
        servlet.service(request, response);
    }
}
