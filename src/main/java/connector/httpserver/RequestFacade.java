package connector.httpserver;


/**
 * @Description 门面模式
 * @Package connector.httpserver
 * @date 2020-04-14
 */
public class RequestFacade {
    private Request request;

    public RequestFacade(Request request) {
        this.request = request;
    }

    public void parse() {
        request.parse();
    }

    public String getUri() {
        return request.getUri();
    }
}
