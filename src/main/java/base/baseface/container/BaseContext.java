package base.baseface.container;

import base.baseface.container.valve.BaseValve;
import base.baseface.session.BaseManager;
import base.face.connector.Request;
import base.face.connector.Response;
import base.face.container.Container;
import base.face.container.Context;
import base.face.session.Manager;
import connector.httpserver.HttpRequest;

public class BaseContext extends BaseContainer implements Context {
    // 是否动态加载servlet
    boolean reload = false;
    // session管理器
    Manager manager;

    public BaseContext() {
        this.manager = new BaseManager();
        this.getPipeline().setBasic(new BaseValve());
    }

    public void setReload(boolean load) {
        this.reload = load;
    }

    private void parseSession(Request request) {
        String cookie = request.getHead().get("Cookie");
        if (cookie != null) {
            String[] kvs = cookie.split(";");
            for (String kv : kvs) {
                String[] strs = kv.split("=");
                if (strs[0].trim().equals("kingSession")) {
                    ((HttpRequest) request).setSession(this.manager.findSession(strs[1].trim()));
                }
            }
        }
    }

    @Override
    public void invoke(Request request, Response response) {
        super.invoke(request, response);
        // request绑定context
        request.setContext(this);
        // 为请求解析session
        parseSession(request);
        // 判断需加载的类
        String uri = request.getUri();
        String servletUri;
        if (uri.startsWith("/servlet")) {
            servletUri = uri.substring(8);
        } else {
            servletUri = uri;
        }
        if (servletUri.startsWith("/")) {
            // 在子容器找到对应servlet
            Container children = findChildren(servletUri.substring(1));
            if (children != null) {
                ((BaseWrapper) children).invoke(request, response);
            }
        }
    }

    @Override
    public Manager getManager() {
        return manager;
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
