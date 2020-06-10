package bootstrap;

import base.baseface.BaseServer;
import base.baseface.BaseService;
import base.baseface.container.BaseContext;
import base.baseface.container.BaseWrapper;
import base.baseface.container.valve.BaseValve;
import base.baseface.container.valve.TestValve;
import base.baseface.container.valve.WrapperValve;
import connector.httpserver.HttpConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * @Description 启动类
 * @Package bootstrap
 * @date 2020-04-14
 */
public class Bootstrap {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Bootstrap.class);
        HttpConnector connector = new HttpConnector();

        // 容器配置
        BaseWrapper wrapper = new BaseWrapper();
        wrapper.setName("TestServlet");
        wrapper.setServletClass("core.servlet.TestServlet");
        wrapper.getPipeline().setBasic(new WrapperValve());
        wrapper.getPipeline().addValve(new TestValve());
        // context容器设置
        BaseContext baseContext = new BaseContext();
        baseContext.addChild(wrapper);
        baseContext.getPipeline().setBasic(new BaseValve());

        // 服务器和服务配置
        BaseServer server = new BaseServer();
        BaseService service = new BaseService();
        server.addService(service);

        // 添加容器和连接器
        service.addConnector(connector);
        service.setContainer(baseContext);
        server.init();
        server.start();
    }
}
