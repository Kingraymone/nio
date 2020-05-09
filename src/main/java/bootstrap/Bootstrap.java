package bootstrap;

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
        logger.info("启动连接！");
        connector.start();
    }
}
