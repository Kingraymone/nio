package connector.httpserver;


/**
 * @Description 门面模式
 * @Package connector.httpserver
 * @date 2020-04-14
 */
public class RequestFacade {
    private HttpRequest httpRequest;

    public RequestFacade(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void parse(String param) {
        try {
            httpRequest.parse(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUri() {
        return httpRequest.getUri();
    }
}
