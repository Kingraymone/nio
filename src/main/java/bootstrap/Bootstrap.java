package bootstrap;

import base.baseface.BaseServer;
import base.baseface.BaseService;
import base.baseface.container.BaseContext;
import base.baseface.container.BaseWrapper;
import base.baseface.container.valve.BaseValve;
import base.baseface.container.valve.TestValve;
import base.baseface.container.valve.WrapperValve;
import com.sun.deploy.util.Property;
import connector.httpserver.HttpConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @Description 启动类
 * @Package bootstrap
 * @date 2020-04-14
 */
public class Bootstrap {
    public static List<BaseWrapper> createWrappers(Properties properties) {
        String[] uris = properties.getProperty("servletName").split(",");
        String[] classNames = properties.getProperty("servletClassName").split(",");
        List<BaseWrapper> lists = new ArrayList<>(uris.length);
        for (int i = 0; i < uris.length; i++) {
            BaseWrapper wrapper = new BaseWrapper();
            if (i == 0) {
                wrapper.getPipeline().addValve(new TestValve());
            }
            wrapper.setName(uris[i]);
            wrapper.setServletClass("core.servlet." + classNames[i]);
            lists.add(wrapper);
        }
        return lists;
    }

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Bootstrap.class);
        HttpConnector connector = new HttpConnector();
        try {
            // 容器配置
            Properties properties = new Properties();
            properties.load(new BufferedInputStream(new FileInputStream("resources/servlet.properties")));
            List<BaseWrapper> list = createWrappers(properties);
            // context容器设置
            BaseContext baseContext = new BaseContext();
            for (BaseWrapper wrapper : list) {
                baseContext.addChild(wrapper);
            }
            // 服务器和服务配置
            BaseServer server = new BaseServer();
            BaseService service = new BaseService();
            server.addService(service);

            // 添加容器和连接器
            service.addConnector(connector);
            service.setContainer(baseContext);
            server.init();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
