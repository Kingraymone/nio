package base.baseface.container;

import base.face.connector.Request;
import base.face.connector.Response;
import base.face.container.Container;
import base.face.container.Context;
import core.servlet.Servlet;

public class BaseContext extends BaseContainer implements Context {
    // 是否动态加载servlet
    boolean reload = false;

    public void setReload(boolean load) {
        this.reload = load;
    }

    @Override
    public void invoke(Request request, Response response) {
        super.invoke(request, response);
        // 判断需加载的类
        String uri = request.getUri();
        String servletUri = uri.substring(8);
        if (servletUri.startsWith("/")) {
            // 在子容器找到对应servlet
            Container children = findChildren(servletUri.substring(1));
            if (children != null) {
                Servlet allocate = ((BaseWrapper) children).allocate();
                allocate.service(request, response);
            }
        }
    }

    @Override
    public void init() {
        logger.debug("init()执行--Context");
    }

    @Override
    public void start() {
        logger.debug("start()执行--Context！");
    }

    @Override
    public void stop() {
        logger.debug("stop()执行--Context！");
    }

}
