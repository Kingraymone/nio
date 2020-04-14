package bootstrap;

import connector.httpserver.HttpConnector;


/**
 * @Description 启动类
 * @Package bootstrap
 * @date 2020-04-14
 */
public class Bootstrap {
    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
