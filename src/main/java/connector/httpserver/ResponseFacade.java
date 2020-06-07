package connector.httpserver;


/**
 * @Description 门面模式
 * @Package connector.httpserver
 * @date 2020-04-14
 */
public class ResponseFacade {
    private HttpResponse httpResponse;

    public ResponseFacade(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public void responseResult() {
        httpResponse.responseStatic();
    }
}
