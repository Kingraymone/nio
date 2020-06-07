package bootstrap;

import base.baseface.BaseServer;
import base.baseface.BaseService;
import base.baseface.container.BaseWrapper;
import base.baseface.container.WrapperValve;
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
        BaseWrapper wrapper = new BaseWrapper();
        wrapper.getPipeline().setBasic(new WrapperValve());
        BaseServer server = new BaseServer();
        BaseService service = new BaseService();
        server.addService(service);

        service.addConnector(connector);
        service.setContainer(wrapper);
        server.init();
        server.start();
    }
}
