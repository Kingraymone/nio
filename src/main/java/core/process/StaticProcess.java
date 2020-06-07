package core.process;

import connector.httpserver.HttpRequest;
import connector.httpserver.HttpResponse;


/**
 * @Description 静态资源处理
 * @Package connector.process
 * @date 2020-04-14
 */
public class StaticProcess {
    public void process(HttpRequest httpRequest, HttpResponse httpResponse){
        httpResponse.responseStatic();
    }
}
